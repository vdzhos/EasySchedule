package com.easyschedule.services.implementations;

import com.easyschedule.exceptions.lesson.InvalidLessonArgumentsException;
import com.easyschedule.exceptions.lesson.NoLessonWithSuchIdFound;
import com.easyschedule.exceptions.subject.SubjectNotFoundException;
import com.easyschedule.exceptions.teacher.TeacherNotFoundException;
import com.easyschedule.models.*;
import com.easyschedule.repositories.LessonRepository;
import com.easyschedule.repositories.SubjectRepository;
import com.easyschedule.repositories.TeacherRepository;
import com.easyschedule.services.interfaces.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class LessonServiceImpl implements LessonService {

    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Override
    public Lesson addLesson(Lesson lesson) {
        return lessonRepository.save(lesson);
    }

    @Override
    public Lesson addLesson(Lesson.Time time, Long subjId, Long teachId, SubjectType subjectType, String weeks, String room, DayOfWeek dayOfWeek) throws Exception {
        Object[] res = verifyAndProcessData(subjId,teachId,weeks,room,time,subjectType,dayOfWeek);
        return lessonRepository.save(new Lesson(time,(Subject) res[1],(Teacher) res[2],subjectType,weeks,(Room) res[0],dayOfWeek));
    }

    @Override
    public boolean existsById(Long id) {
        return lessonRepository.existsById(id);
    }

    @Override
    @Transactional
    public boolean deleteLesson(Long id) throws NoLessonWithSuchIdFound {
        if(!existsById(id)){
            throw new NoLessonWithSuchIdFound(id);
        }
        lessonRepository.deleteById(id);
        return true;
    }

    @Override
    public Lesson updateLesson(Lesson lesson) throws NoLessonWithSuchIdFound {
        if(!existsById(lesson.getId())) throw new NoLessonWithSuchIdFound(lesson.getId());
        return lessonRepository.save(lesson);
    }

    @Override
    public Lesson updateLesson(Long id, Lesson.Time time, Long subjId, Long teachId, SubjectType subjectType, String weeks, String room, DayOfWeek dayOfWeek) throws Exception {
        Object[] res = verifyAndProcessData(subjId,teachId,weeks,room,time,subjectType,dayOfWeek);
        return lessonRepository.save(new Lesson(id,time,(Subject) res[1],(Teacher) res[2],subjectType,weeks,(Room) res[0],dayOfWeek));
    }

    @Override
    public Lesson getLesson(Long id) throws NoLessonWithSuchIdFound{
        return lessonRepository.findById(id).orElseThrow(() -> new NoLessonWithSuchIdFound(id));
    }

    @Override
    public List<Lesson> getAll() {
        return (List<Lesson>) lessonRepository.findAll();
    }

    private Object[] verifyAndProcessData(Long subjId, Long teachId, String weeks, String room,Lesson.Time time, SubjectType subjectType,  DayOfWeek dayOfWeek) throws Exception {
        Room r;
        if(room.equals("remotely")) r = new Room();
        else r = new Room(room);

        List<Integer> weeksList = Stream.of(weeks.split("[,-]")).map(Integer::parseInt).collect(Collectors.toList());

        if(weeks.isEmpty() || !weeks.matches("^([1-9][0-9]*(-[1-9][0-9]*)?)(,([1-9][0-9]*(-[1-9][0-9]*)?))*$") || !checkWeeksAscending(weeksList)){
            throw new InvalidLessonArgumentsException("weeks",weeks);
        }

        Optional<Subject> s = subjectRepository.findById(subjId);
        Optional<Teacher> t = teacherRepository.findById(teachId);

        if(s.isEmpty()){
            throw new SubjectNotFoundException(subjId);
        }
        if(t.isEmpty()){
            throw new TeacherNotFoundException("Teacher with id \""+teachId+"\" not found!");
        }

        for (Lesson l : t.get().getLessons()) {
            Set<Integer> w = l.getIntWeeks().stream().distinct().filter(weeksList::contains).collect(Collectors.toSet());
            boolean sameTime = l.getDayOfWeek()==dayOfWeek && l.getTime() == time && !w.isEmpty();

            if(sameTime){
                if(!l.getSubject().getId().equals(s.get().getId())){
                    throw new Exception("Teacher can't teach different lessons at the same time!");
                }else if(!l.getRoom().getRoom().equals(room)){
                    throw new Exception("Teacher can't have lessons in different rooms at the same time!");
                }else if(l.getGroup().getGroup().equals(subjectType.getGroup())){
                    throw new Exception("Duplicate lesson for same group!");
                }else if(!(l.getGroup().getType() == SubjectType.SubjectTypeEnum.PRACTICE && subjectType.getType() == SubjectType.SubjectTypeEnum.PRACTICE)){
                    throw new Exception("Teacher can't have lecture and practice at the same time!");
                }
            }
        }

        return new Object[]{r,s.get(),t.get()};
    }

    private boolean checkWeeksAscending(List<Integer> w){
        boolean ok = true;
        for (int i = 1; i < w.size(); i++) {
            if(w.get(i)<=w.get(i-1)) {
                ok = false;
                break;
            }
        }
        return ok;
    }

}
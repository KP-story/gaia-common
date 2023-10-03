package com.delight.gaia.auth.context;

import com.delight.gaia.auth.subject.Subject;
import com.delight.gaia.base.vo.UserDisplayInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class SecurityUtils {
    private static SubjectHolder subjectHolder;

    @Autowired
    public void setSubjectHolder(SubjectHolder subjectHolder) {
        SecurityUtils.subjectHolder = subjectHolder;
    }

    public static Mono<Subject> getRequester() {
        return subjectHolder.getSubject();
    }


}

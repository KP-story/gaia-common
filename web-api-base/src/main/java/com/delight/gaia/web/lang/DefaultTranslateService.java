package com.delight.gaia.web.lang;

import com.delight.gaia.base.lang.TranslateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
@Slf4j
public class DefaultTranslateService implements TranslateService {
    MessageSource messageSource;

    @Autowired
    public DefaultTranslateService(MessageSource messageSource) {
        this.messageSource = messageSource;
    }


    @Override
    public String translate(Locale locale, String content) {
        try {

            return messageSource.getMessage(content, null, locale != null ? locale : new Locale("en"));
        } catch (Exception e) {
            log.error("translate {}  has error", content, e);
            return content;
        }
    }

    @Override
    public String translateWithArgs(Locale locale, String content, Object... args) {
        try {
            return messageSource.getMessage(content, args, locale != null ? locale : new Locale("en"));
        } catch (Exception e) {
            log.error("translate {}  has error", content, e);
            return content;
        }
    }

    @Override
    public String translateWithLang(String lang, String content) {
        try {
            return messageSource.getMessage(content, null, new Locale(lang));
        } catch (Exception e) {
            log.error("translate {}  has error", content, e);
            return content;
        }
    }

    @Override
    public String translateWithLangAndArgs(String lang, String content, Object... args) {
        try {
            return messageSource.getMessage(content, args, new Locale(lang));
        } catch (Exception e) {
            log.error("translate {}  has error", content, e);
            return content;
        }
    }

}

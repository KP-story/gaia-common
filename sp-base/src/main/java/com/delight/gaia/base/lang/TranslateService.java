package com.delight.gaia.base.lang;

import java.util.Locale;

public interface TranslateService {

    String translate( Locale locale,String content);

    String translateWithArgs(Locale locale,String content, Object... args);

    String translateWithLang(String lang, String content);

    String translateWithLangAndArgs(String lang, String content, Object... args);
}

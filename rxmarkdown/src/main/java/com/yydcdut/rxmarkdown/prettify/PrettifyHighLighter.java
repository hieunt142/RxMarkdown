/*
 * Copyright (C) 2017 yydcdut (yuyidong2015@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.yydcdut.rxmarkdown.prettify;

import android.support.annotation.NonNull;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;

import com.yydcdut.rxmarkdown.RxMDConfiguration;
import com.yydcdut.rxmarkdown.theme.Theme;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import prettify.PrettifyParser;
import syntaxhighlight.ParseResult;
import syntaxhighlight.Parser;

/**
 * Created by yuyidong on 2017/5/26.
 */
public class PrettifyHighLighter {
    private Map<String, Integer> mColorMap;
    private Parser mParser;

    public PrettifyHighLighter(@NonNull RxMDConfiguration rxMDConfiguration) {
        mColorMap = buildColorsMap(rxMDConfiguration.getTheme());
        mParser = new PrettifyParser();
    }

    public SpannableStringBuilder highLight(String language, SpannableStringBuilder sourceCode, int start, int end) {
        List<ParseResult> results = mParser.parse(language, sourceCode.toString().substring(start, end));
        for (ParseResult result : results) {
            String type = result.getStyleKeys().get(0);
            sourceCode.setSpan(new ForegroundColorSpan(getColor(type)), start + result.getOffset(), start + result.getOffset() + result.getLength(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return sourceCode;
    }

    private int getColor(String type) {
        return mColorMap.containsKey(type) ? mColorMap.get(type) : mColorMap.get(Theme.CODE_PLN);
    }

    private static Map<String, Integer> buildColorsMap(Theme theme) {//todo utils
        Map<String, Integer> map = new HashMap<>();
        map.put(Theme.CODE_TYP, theme.getTypColor());
        map.put(Theme.CODE_KWD, theme.getKwdColor());
        map.put(Theme.CODE_LIT, theme.getLitColor());
        map.put(Theme.CODE_COM, theme.getComColor());
        map.put(Theme.CODE_STR, theme.getStrColor());
        map.put(Theme.CODE_PUN, theme.getPunColor());
        map.put(Theme.CODE_TAG, theme.getTagColor());
        map.put(Theme.CODE_PLN, theme.getPlnColor());
        map.put(Theme.CODE_DEC, theme.getDecColor());
        map.put(Theme.CODE_ATN, theme.getAtnColor());
        map.put(Theme.CODE_ATV, theme.getAtvColor());
        map.put(Theme.CODE_OPN, theme.getOpnColor());
        map.put(Theme.CODE_CLO, theme.getCloColor());
        map.put(Theme.CODE_VAR, theme.getVarColor());
        map.put(Theme.CODE_FUN, theme.getFunColor());
        map.put(Theme.CODE_NOCODE, theme.getNocodeColor());
        return map;
    }
}
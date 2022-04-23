/*
 * MIT License
 *
 * Copyright (c) 2021 Przemysław Płóciennik
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.github.pplociennik.auth.common.utility.properties;

import com.github.pplociennik.auth.common.exc.ReadingPropertiesException;

/**
 * A util for reading language properties.
 *
 * @author Created by: Pplociennik at 29.01.2022 20:14
 */
public final class LanguagePropertiesReader extends PropertiesReader {

    private static final String LANGUAGE_PROPERTIES_FILE = "lang/properties/translation.properties";

    /**
     * Returns a basename for translations file.
     *
     * @return a value of the 'basename' property
     */
    public String getBasename() {
        String baseNameProperty;

        try {
            baseNameProperty = readProperty( LANGUAGE_PROPERTIES_FILE, "message.language.basename" );
        } catch ( ReadingPropertiesException aE ) {
            throw new ReadingPropertiesException( "Could not find 'BASENAME' property." );
        }

        return baseNameProperty;
    }
}

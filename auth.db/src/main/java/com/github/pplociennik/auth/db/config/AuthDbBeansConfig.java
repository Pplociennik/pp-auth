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

package com.github.pplociennik.auth.db.config;

import com.github.pplociennik.auth.db.entity.authentication.Account;
import com.github.pplociennik.auth.db.entity.authorization.Authority;
import com.github.pplociennik.auth.db.repository.authentication.AccountDao;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * A Spring's configuration class for db module beans and configs definition.
 *
 * @author Created by: Pplociennik at 03.02.2022 18:26
 */
@Configuration
@EnableJpaRepositories( basePackageClasses = { AccountDao.class } )
@EntityScan( basePackageClasses = { Account.class, Authority.class } )
public class AuthDbBeansConfig {
}

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

package com.github.pplociennik.auth.db.entity.authorization;

import com.github.pplociennik.auth.db.entity.authentication.Account;
import lombok.*;

import javax.persistence.*;

/**
 * An Entity class being the representation of user's authority.
 *
 * @author Created by: Pplociennik at 17.09.2021 20:25
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
@Entity
@Table( name = "AUTH_AUTHORITIES" )
public class Authority {

    /**
     * An identifier of the object.
     */
    @Id
    @GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "AUTH_AUTHORITY_GEN" )
    @SequenceGenerator( name = "AUTH_AUTHORITY_GEN", sequenceName = "SEQ_AUTH_AUTHORITY", allocationSize = 1 )
    @Column( name = "ID", nullable = false, unique = true )
    private Long id;

    /**
     * A string being a name of the authority. The name is being using during the authorization process.
     */
    @Column( name = "AUTHORITY_NAME", nullable = false )
    private String name;

    /**
     * An {@link Account} which the authority object is related to.
     */
    @ManyToOne( fetch = FetchType.EAGER, optional = false )
    @JoinColumn( name = "AUTHORITIES_OWNER", referencedColumnName = "ID" )
    private Account authoritiesOwner;


}

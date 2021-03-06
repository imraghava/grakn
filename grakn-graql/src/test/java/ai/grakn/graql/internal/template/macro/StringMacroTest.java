/*
 * Grakn - A Distributed Semantic Database
 * Copyright (C) 2016  Grakn Labs Limited
 *
 * Grakn is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Grakn is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Grakn. If not, see <http://www.gnu.org/licenses/gpl.txt>.
 */

package ai.grakn.graql.internal.template.macro;

import ai.grakn.exception.GraqlQueryException;
import com.google.common.collect.ImmutableList;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Collections;
import java.util.Map;

import static ai.grakn.graql.internal.template.macro.MacroTestUtilities.assertParseEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class StringMacroTest {

    private final StringMacro stringMacro = new StringMacro();

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void applyStringMacroToNoArguments_ExceptionIsThrown(){
        exception.expect(GraqlQueryException.class);
        exception.expectMessage("Wrong number of arguments");

        stringMacro.apply(Collections.emptyList());
    }

    @Test
    public void applyStringMacroToMoreThanOneArgument_ExceptionIsThrown(){
        exception.expect(GraqlQueryException.class);
        exception.expectMessage("Wrong number of arguments");

        stringMacro.apply(ImmutableList.of("1.0", "2.0"));
    }

    @Test
    public void applyStringMacroToOneArgument_ItReturnsNonNull(){
        assertNotNull(stringMacro.apply(ImmutableList.of("1")));
    }

    @Test
    public void applyStringMacroToInt_ReturnsCorrectString(){
        assertEquals("1", stringMacro.apply(ImmutableList.of(1)));
    }

    @Test
    public void applyStringMacroToObject_ReturnsCorrectString(){
        Object object = new Object();
        assertEquals(object.toString(), stringMacro.apply(ImmutableList.of(object)));
    }

    @Test
    public void whenUsingSplitMacroInTemplate_ItExecutesCorrectly(){
        String template = "insert $this val @string(<value>);";
        String expected = "insert $this0 val \"1000\";";

        Map<String, Object> data = Collections.singletonMap("value", 1000);

        assertParseEquals(template, data, expected);
    }
}

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
 *
 */

package ai.grakn.graql.internal.pattern.property;

import ai.grakn.concept.Concept;
import ai.grakn.exception.GraqlQueryException;
import ai.grakn.graql.Pattern;
import ai.grakn.graql.Var;
import ai.grakn.graql.admin.Atomic;
import ai.grakn.graql.admin.ReasonerQuery;
import ai.grakn.graql.admin.UniqueVarProperty;
import ai.grakn.graql.admin.VarPatternAdmin;
import ai.grakn.graql.internal.gremlin.EquivalentFragmentSet;
import ai.grakn.graql.internal.query.InsertQueryExecutor;
import ai.grakn.util.ErrorMessage;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Set;

import static ai.grakn.util.Schema.MetaSchema.RULE;

/**
 * Abstract property for the patterns within rules.
 *
 * @author Felix Chapman
 */
public abstract class RuleProperty extends AbstractVarProperty implements UniqueVarProperty, NamedProperty {
    protected final Pattern pattern;

    RuleProperty(Pattern pattern) {
        this.pattern = pattern;
    }

    public Pattern getPattern() {
        return pattern;
    }

    @Override
    public String getProperty() {
        return pattern.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RuleProperty that = (RuleProperty) o;

        return pattern.equals(that.pattern);

    }

    @Override
    public int hashCode() {
        return pattern.hashCode();
    }

    @Override
    public Collection<EquivalentFragmentSet> match(Var start) {
        throw new UnsupportedOperationException(ErrorMessage.MATCH_INVALID.getMessage(this.getClass().getName()));
    }

    @Override
    public void insert(InsertQueryExecutor insertQueryExecutor, Concept concept) throws GraqlQueryException {
        if (!concept.isRule()) {
            throw GraqlQueryException.insertUnsupportedProperty(this.getName(), RULE);
        }
    }

    @Nullable
    @Override
    public Atomic mapToAtom(VarPatternAdmin var, Set<VarPatternAdmin> vars, ReasonerQuery parent) {
        return null;
    }
}

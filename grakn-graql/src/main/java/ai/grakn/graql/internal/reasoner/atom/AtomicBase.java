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

package ai.grakn.graql.internal.reasoner.atom;

import ai.grakn.GraknGraph;
import ai.grakn.graql.Var;
import ai.grakn.graql.admin.Atomic;
import ai.grakn.graql.admin.PatternAdmin;
import ai.grakn.graql.admin.ReasonerQuery;
import ai.grakn.graql.admin.VarPatternAdmin;

import com.google.common.collect.Sets;

import java.util.Set;

/**
 *
 * <p>
 * Base {@link Atomic} implementation providing basic functionalities.
 * </p>
 *
 * @author Kasper Piskorski
 *
 */
public abstract class AtomicBase implements Atomic {

    private final Var varName;
    private final PatternAdmin atomPattern;
    private ReasonerQuery parent = null;

    protected AtomicBase(VarPatternAdmin pattern, ReasonerQuery par) {
        this.atomPattern = pattern;
        this.varName = pattern.getVarName();
        this.parent = par;
    }

    protected AtomicBase(AtomicBase a) {
        this.atomPattern = a.atomPattern;
        this.varName = atomPattern.asVar().getVarName();
        this.parent = a.getParentQuery();
    }

    @Override
    public abstract Atomic copy();

    @Override
    public String toString(){ return atomPattern.toString(); }

    @Override
    public boolean containsVar(Var name){ return getVarNames().contains(name);}

    @Override
    public boolean isUserDefinedName(){ return atomPattern.asVar().getVarName().isUserDefinedName();}
    
    @Override
    public Var getVarName(){ return varName;}

    @Override
    public Set<Var> getVarNames(){
        return Sets.newHashSet(varName);
    }

    /**
     * @return pattern corresponding to this atom
     */
    public PatternAdmin getPattern(){ return atomPattern;}
    public PatternAdmin getCombinedPattern(){ return getPattern();}

    /**
     * @return the query the atom is contained in
     */
    public ReasonerQuery getParentQuery(){ return parent;}

    /**
     * @param q query this atom is supposed to belong to
     */
    public void setParentQuery(ReasonerQuery q){ parent = q;}
    protected GraknGraph graph(){ return getParentQuery().graph();}
}


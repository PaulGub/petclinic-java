/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.model;

import javax.persistence.OneToMany;
import javax.persistence.CascadeType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlElement;

import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PropertyComparator;

/**
 * Simple JavaBean domain object representing a veterinarian.
 *
 * @author Ken Krebs
 * @author Juergen Hoeller
 * @author Sam Brannen
 * @author Arjen Poutsma
 */
@Entity
@Table(name = "vets")
public class Vet extends Person {

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "vet_specialties", joinColumns = @JoinColumn(name = "vet_id"),
        inverseJoinColumns = @JoinColumn(name = "specialty_id"))
    private Set<Specialty> specialties;

    protected Set<Specialty> getSpecialtiesInternal() {
        if (this.specialties == null) {
            this.specialties = new HashSet<>();
        }
        return this.specialties;
    }

    protected void setSpecialtiesInternal(Set<Specialty> specialties) {
        this.specialties = specialties;
    }

    @XmlElement
    public List<Specialty> getSpecialties() {
        List<Specialty> sortedSpecs = new ArrayList<>(getSpecialtiesInternal());
        PropertyComparator.sort(sortedSpecs, new MutableSortDefinition("name", true, true));
        return Collections.unmodifiableList(sortedSpecs);
    }

    public int getNrOfSpecialties() {
        return getSpecialtiesInternal().size();
    }

    public void addSpecialty(Specialty specialty) {
        getSpecialtiesInternal().add(specialty);
    }

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "vet", fetch = FetchType.EAGER)
    private Set<Memo> memos;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "vet", fetch = FetchType.EAGER)
    private Set<Operation> operations;

    protected Set<Memo> getMemosInternal() {
        if (this.memos == null) {
            this.memos = new HashSet<>();
        }
        return this.memos;
    }

    protected void setMemosInternal(Set<Memo> memos) {
        this.memos = memos;
    }

    public List<Memo> getMemos() {
        List<Memo> sortedMemos = new ArrayList<>(getMemosInternal());
        PropertyComparator.sort(sortedMemos, new MutableSortDefinition("date", true, true));
        return Collections.unmodifiableList(sortedMemos);
    }

    public void addMemo(Memo memo) {
        getMemosInternal().add(memo);
        memo.setVet(this);
    }

    public int getNrOfMemos() {
        return getMemosInternal().size();
    }

    protected Set<Operation> getOperationsInternal() {
        if (this.operations == null) {
            this.operations = new HashSet<>();
        }
        return this.operations;
    }

    protected void setOperationsInternal(Set<Operation> operations) {
        this.operations = operations;
    }

    public List<Operation> getOperations() {
        List<Operation> sortedOperations = new ArrayList<>(getOperationsInternal());
        PropertyComparator.sort(sortedOperations, new MutableSortDefinition("date", true, true));
        return Collections.unmodifiableList(sortedOperations);
    }

    public void addOperation(Operation operation) {
        getOperationsInternal().add(operation);
        operation.setVet(this);
    }

    public int getNrOfOperations() {
        return getOperationsInternal().size();
    }

}

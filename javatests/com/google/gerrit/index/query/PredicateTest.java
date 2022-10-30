// Copyright (C) 2015 The Android Open Source Project
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.gerrit.index.query;

import org.junit.Ignore;

@Ignore
public abstract class PredicateTest {
<<<<<<< HEAD   (79da1c Initialise the project name / change number eagerly in gr-ro)
  @SuppressWarnings("ProtectedMembersInFinalClass")
  protected static final class TestMatchablePredicate extends TestPredicate
      implements Matchable<String> {
=======
  protected static class TestDataSourcePredicate extends TestMatchablePredicate<String>
      implements DataSource<String> {
    protected final int cardinality;

    protected TestDataSourcePredicate(String name, String value, int cost, int cardinality) {
      super(name, value, cost);
      this.cardinality = cardinality;
    }

    @Override
    public int getCardinality() {
      return cardinality;
    }

    @Override
    public ResultSet<String> read() {
      return null;
    }

    @Override
    public ResultSet<FieldBundle> readRaw() {
      return null;
    }
  }

  protected static class TestMatchablePredicate<T> extends TestPredicate<T>
      implements Matchable<T> {
>>>>>>> BRANCH (a9f258 Merge branch 'stable-3.4' into stable-3.5)
    protected int cost;
    protected boolean ranMatch = false;

    protected TestMatchablePredicate(String name, String value, int cost) {
      super(name, value);
      this.cost = cost;
    }

    @Override
    public boolean match(T object) {
      ranMatch = true;
      return false;
    }

    @Override
    public int getCost() {
      return cost;
    }
  }

<<<<<<< HEAD   (79da1c Initialise the project name / change number eagerly in gr-ro)
  protected static class TestPredicate extends OperatorPredicate<String> {
    private TestPredicate(String name, String value) {
=======
  protected static class TestPredicate<T> extends OperatorPredicate<T> {
    protected TestPredicate(String name, String value) {
>>>>>>> BRANCH (a9f258 Merge branch 'stable-3.4' into stable-3.5)
      super(name, value);
    }
  }

  protected static TestPredicate<String> f(String name, String value) {
    return new TestPredicate<>(name, value);
  }
}

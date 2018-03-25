// Copyright (C) 2016 The Android Open Source Project
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

package com.google.gerrit.server.query.account;

import com.google.common.collect.Lists;
import com.google.common.primitives.Ints;
import com.google.gerrit.index.FieldDef;
import com.google.gerrit.index.query.IndexPredicate;
import com.google.gerrit.index.query.Matchable;
import com.google.gerrit.index.query.Predicate;
import com.google.gerrit.index.query.QueryBuilder;
import com.google.gerrit.reviewdb.client.Account;
import com.google.gerrit.reviewdb.client.Project;
import com.google.gerrit.server.account.AccountState;
import com.google.gerrit.server.index.account.AccountField;
import com.google.gerrit.server.notedb.ChangeNotes;
<<<<<<< HEAD   (08606b Limit assignee suggestions to users that can see the change)
=======
import com.google.gerrit.server.query.Matchable;
import com.google.gerrit.server.query.Predicate;
import com.google.gerrit.server.query.QueryBuilder;
>>>>>>> BRANCH (354f9e Limit assignee suggestions to users that can see the change)
import com.google.gwtorm.server.OrmException;
import java.util.List;

public class AccountPredicates {
  public static boolean hasActive(Predicate<AccountState> p) {
    return QueryBuilder.find(p, AccountPredicate.class, AccountField.ACTIVE.getName()) != null;
  }

  public static Predicate<AccountState> andActive(Predicate<AccountState> p) {
    return Predicate.and(p, isActive());
  }

  public static Predicate<AccountState> defaultPredicate(String query) {
    // Adapt the capacity of this list when adding more default predicates.
    List<Predicate<AccountState>> preds = Lists.newArrayListWithCapacity(3);
    Integer id = Ints.tryParse(query);
    if (id != null) {
      preds.add(id(new Account.Id(id)));
    }
    preds.add(equalsName(query));
    preds.add(username(query));
    // Adapt the capacity of the "predicates" list when adding more default
    // predicates.
    return Predicate.or(preds);
  }

  public static Predicate<AccountState> id(Account.Id accountId) {
    return new AccountPredicate(
        AccountField.ID, AccountQueryBuilder.FIELD_ACCOUNT, accountId.toString());
  }

  public static Predicate<AccountState> email(String email) {
    return new AccountPredicate(
        AccountField.EMAIL, AccountQueryBuilder.FIELD_EMAIL, email.toLowerCase());
  }

  public static Predicate<AccountState> preferredEmail(String email) {
    return new AccountPredicate(
        AccountField.PREFERRED_EMAIL,
        AccountQueryBuilder.FIELD_PREFERRED_EMAIL,
        email.toLowerCase());
  }

  public static Predicate<AccountState> preferredEmailExact(String email) {
    return new AccountPredicate(
        AccountField.PREFERRED_EMAIL_EXACT, AccountQueryBuilder.FIELD_PREFERRED_EMAIL_EXACT, email);
  }

  public static Predicate<AccountState> equalsName(String name) {
    return new AccountPredicate(
        AccountField.NAME_PART, AccountQueryBuilder.FIELD_NAME, name.toLowerCase());
  }

  public static Predicate<AccountState> externalId(String externalId) {
    return new AccountPredicate(AccountField.EXTERNAL_ID, externalId);
  }

  public static Predicate<AccountState> fullName(String fullName) {
    return new AccountPredicate(AccountField.FULL_NAME, fullName);
  }

  public static Predicate<AccountState> isActive() {
    return new AccountPredicate(AccountField.ACTIVE, "1");
  }

  public static Predicate<AccountState> isNotActive() {
    return new AccountPredicate(AccountField.ACTIVE, "0");
  }

  public static Predicate<AccountState> username(String username) {
    return new AccountPredicate(
        AccountField.USERNAME, AccountQueryBuilder.FIELD_USERNAME, username.toLowerCase());
  }

  public static Predicate<AccountState> watchedProject(Project.NameKey project) {
    return new AccountPredicate(AccountField.WATCHED_PROJECT, project.get());
  }

<<<<<<< HEAD   (08606b Limit assignee suggestions to users that can see the change)
  public static Predicate<AccountState> cansee(
      AccountQueryBuilder.Arguments args, ChangeNotes changeNotes) {
    return new CanSeeChangePredicate(
        args.db, args.permissionBackend, args.userFactory, changeNotes);
=======
  static Predicate<AccountState> cansee(
      AccountQueryBuilder.Arguments args, ChangeNotes changeNotes) {
    return new CanSeeChangePredicate(
        args.db, args.changeControlFactory, args.userFactory, changeNotes);
>>>>>>> BRANCH (354f9e Limit assignee suggestions to users that can see the change)
  }

  static class AccountPredicate extends IndexPredicate<AccountState>
      implements Matchable<AccountState> {
    AccountPredicate(FieldDef<AccountState, ?> def, String value) {
      super(def, value);
    }

    AccountPredicate(FieldDef<AccountState, ?> def, String name, String value) {
      super(def, name, value);
    }

    @Override
    public boolean match(AccountState object) throws OrmException {
      return true;
    }

    @Override
    public int getCost() {
      return 1;
    }
  }

  private AccountPredicates() {}
}

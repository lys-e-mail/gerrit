// Copyright (C) 2009 The Android Open Source Project
//
// Licensed under the Apache License, Version 2.0 (the "License");


   * <p>This method first loads the external ID for the username and then uses the account ID of the
   * external ID to lookup the account from the cache.
   *
   * @param username username of the account that should be retrieved
   * @return {@code AccountState} instance for the given username, if no account with this username
   *     exists or if loading the external ID fails {@link Optional#empty()} is returned
   */
  Optional<AccountState> getByUsername(String username);

  /**
   * Returns an {@code AccountState} instance for the given account ID at the given {@code metaId}
   * of {@link com.google.gerrit.entities.RefNames#refsUsers} ref.
   *
   * <p>The caller is responsible to ensure the presence of {@code metaId} and the corresponding
   * meta ref. The method does not populate {@link AccountState#defaultPreferences}.
   *
   * @param accountId ID of the account that should be retrieved.
   * @param metaId the sha1 of commit in {@link com.google.gerrit.entities.RefNames#refsUsers} ref.
   * @return {@code AccountState} instance for the given account ID at specific sha1 {@code metaId}.
   */
  @UsedAt(Project.GOOGLE)
  AccountState getFromMetaId(Account.Id accountId, ObjectId metaId);
}

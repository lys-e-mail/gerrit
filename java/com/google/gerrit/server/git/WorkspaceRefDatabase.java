package com.google.gerrit.server.git;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.RefDatabase;
import org.eclipse.jgit.lib.RefRename;
import org.eclipse.jgit.lib.RefUpdate;

public class WorkspaceRefDatabase extends RefDatabase {
  private final RefDatabase workspace;
  private final MainlineReadOnlyRefDatabase mainline;

  WorkspaceRefDatabase(RefDatabase workspace, MainlineReadOnlyRefDatabase mainline) {
    this.workspace = workspace;
    this.mainline = mainline;
  }

  @Override
  public boolean performsAtomicTransactions() {
    return true;
  }

  @Override
  public void create() throws IOException {
    workspace.create();
  }

  @Override
  public void close() {
    workspace.close();
    mainline.close();
  }

  @Override
  public boolean isNameConflicting(String name) throws IOException {
    return workspace.isNameConflicting(name);
  }

  @Override
  public RefUpdate newUpdate(String name, boolean detach) throws IOException {
    return workspace.newUpdate(name, detach);
  }

  @Override
  public RefRename newRename(String fromName, String toName) throws IOException {
    return workspace.newRename(fromName, toName);
  }

  @Override
  public Ref exactRef(String name) throws IOException {
    Ref r = workspace.exactRef(name);
    if (r != null) {
      return r;
    }
    return mainline.exactRef(name);
  }

  @Override
  public Map<String, Ref> getRefs(String prefix) throws IOException {
    Map<String, Ref> r = new HashMap<>();
    r.putAll(workspace.getRefs(prefix));
    r.putAll(mainline.getRefs(prefix));
    return r;
  }

  @Override
  public List<Ref> getAdditionalRefs() throws IOException {
    List<Ref> r = new ArrayList<>();
    r.addAll(workspace.getAdditionalRefs());
    r.addAll(mainline.getAdditionalRefs());
    return r;
  }

  @Override
  public Ref peel(Ref ref) throws IOException {
    return workspace.peel(ref) == null ? mainline.peel(ref) : workspace.peel(ref);
  }
}

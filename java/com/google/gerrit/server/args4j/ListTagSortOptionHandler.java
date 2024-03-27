package com.google.gerrit.server.args4j;

import static com.google.gerrit.util.cli.Localizable.localizable;

import com.google.gerrit.extensions.common.ListTagSortOption;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.OptionDef;
import org.kohsuke.args4j.spi.OptionHandler;
import org.kohsuke.args4j.spi.Parameters;
import org.kohsuke.args4j.spi.Setter;

public class ListTagSortOptionHandler extends OptionHandler<ListTagSortOption> {
  @Inject
  public ListTagSortOptionHandler(
      @Assisted CmdLineParser parser,
      @Assisted OptionDef option,
      @Assisted Setter<ListTagSortOption> setter) {
    super(parser, option, setter);
  }

  @Override
  public int parseArguments(Parameters params) throws CmdLineException {
    String param = params.getParameter(0);
    try {
      setter.addValue(ListTagSortOption.valueOf(param.toUpperCase()));
      return 1;
    } catch (IllegalArgumentException e) {
      throw new CmdLineException(
          owner, localizable("\"%s\" is not a valid sort option: %s"), param, e.getMessage());
    }
  }

  @Override
  public String getDefaultMetaVariable() {
    return "REF,CREATION_TIME";
  }
}

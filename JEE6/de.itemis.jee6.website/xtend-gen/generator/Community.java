package generator;

import generator.AbstractWebsite;
import org.eclipse.xtend2.lib.StringConcatenation;

@SuppressWarnings("all")
public class Community extends AbstractWebsite {
  public String path() {
    return "community.html";
  }
  
  public CharSequence contents() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("<div id=\"header_wrapper\" class=\"container\" >");
    _builder.newLine();
    _builder.append("</div>");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("<div id=\"page\">");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("<div id=\"team\" class=\"container clearfix\"> ");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("<h2>Community</h2>");
    _builder.newLine();
    _builder.append("\t        ");
    _builder.append("<hr>");
    _builder.newLine();
    _builder.append("\t\t\t");
    StringConcatenation _builder_1 = new StringConcatenation();
    _builder_1.append("icon-bug-blue.png");
    _builder_1.newLine();
    StringConcatenation _builder_2 = new StringConcatenation();
    _builder_2.append("Bug gefunden?");
    _builder_2.newLine();
    StringConcatenation _builder_3 = new StringConcatenation();
    _builder_3.append("mailto:jee6-generator@itemis.de");
    _builder_3.newLine();
    StringConcatenation _builder_4 = new StringConcatenation();
    _builder_4.append("Du hast einen Bug gefunden oder einen Verbesserungsvorschlag? Sende uns ein Mail an <strong>jee6-generator@itemis.de</strong>");
    _builder_4.newLine();
    CharSequence _entry = this.entry(_builder_1.toString(), _builder_2.toString(), _builder_3.toString(), _builder_4.toString());
    _builder.append(_entry, "\t\t\t");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t");
    _builder.append("</div>");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("</div>");
    _builder.newLine();
    return _builder;
  }
  
  public CharSequence entry(final String img, final String title, final String link, final String description) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("<div class=\"row\">");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("<div class=\"span1\">&nbsp;</div>");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("<div class=\"span9 team\">");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("<a href=\"");
    String _trim = link.trim();
    _builder.append(_trim, "    ");
    _builder.append("\" class=\"anchor-in-div\"></a>");
    _builder.newLineIfNotEmpty();
    _builder.append("    ");
    _builder.append("<div class=\"row\">");
    _builder.newLine();
    _builder.append("\t  ");
    _builder.append("<div class=\"span1 \">");
    _builder.newLine();
    _builder.append("\t    ");
    _builder.append("<img src=\"images/");
    String _trim_1 = img.trim();
    _builder.append(_trim_1, "\t    ");
    _builder.append("\" alt=\"image\" class=\"image_left\">");
    _builder.newLineIfNotEmpty();
    _builder.append("\t  ");
    _builder.append("</div>");
    _builder.newLine();
    _builder.append("\t  ");
    _builder.append("<div class=\"span2 \">");
    _builder.newLine();
    _builder.append("\t    ");
    _builder.append("<h3>");
    String _trim_2 = title.trim();
    _builder.append(_trim_2, "\t    ");
    _builder.append("</h3>");
    _builder.newLineIfNotEmpty();
    _builder.append("\t \t");
    _builder.append("</div>");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("<div class=\"span6 \">");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append(description, "\t\t\t");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t");
    _builder.append("</div>");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("</div>");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("</div>");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("<div class=\"span1\">&nbsp;</div>");
    _builder.newLine();
    _builder.append("</div>");
    _builder.newLine();
    return _builder;
  }
}

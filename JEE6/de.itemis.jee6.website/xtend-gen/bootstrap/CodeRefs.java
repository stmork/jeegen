package bootstrap;

import com.google.common.base.Objects;
import com.google.inject.Inject;
import java.util.HashSet;
import org.eclipse.xtext.common.types.JvmIdentifiableElement;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.InputOutput;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.StringExtensions;
import org.eclipse.xtext.xdoc.generator.util.GitExtensions;
import org.eclipse.xtext.xdoc.generator.util.JavaDocExtension;

@SuppressWarnings("all")
public class CodeRefs {
  @Inject
  @Extension
  private GitExtensions _gitExtensions;
  
  @Inject
  @Extension
  private JavaDocExtension _javaDocExtension;
  
  private final static HashSet<String> NO_JAVADOC_PACKAGE_PREFIXES = CollectionLiterals.<String>newHashSet("org.xtext.");
  
  private final static HashSet<String> NO_SOURCE_PACKAGE_PREFIXES = CollectionLiterals.<String>newHashSet("java.", "org.eclipse.", "javax.", "com.mongodb.", "com.google.", "org.junit.");
  
  public String getJavaDocURI(final JvmIdentifiableElement element) {
    String _xblockexpression = null;
    {
      final String uri = this._javaDocExtension.genJavaDocLink(element);
      boolean _and = false;
      boolean _isNullOrEmpty = StringExtensions.isNullOrEmpty(uri);
      if (!_isNullOrEmpty) {
        _and = false;
      } else {
        final Function1<String,Boolean> _function = new Function1<String,Boolean>() {
          public Boolean apply(final String it) {
            boolean _xblockexpression = false;
            {
              String _identifier = null;
              if (element!=null) {
                _identifier=element.getIdentifier();
              }
              final String identifier = _identifier;
              boolean _and = false;
              boolean _notEquals = (!Objects.equal(identifier, null));
              if (!_notEquals) {
                _and = false;
              } else {
                boolean _startsWith = identifier.startsWith(it);
                _and = _startsWith;
              }
              _xblockexpression = _and;
            }
            return Boolean.valueOf(_xblockexpression);
          }
        };
        boolean _exists = IterableExtensions.<String>exists(CodeRefs.NO_JAVADOC_PACKAGE_PREFIXES, _function);
        boolean _not = (!_exists);
        _and = _not;
      }
      if (_and) {
        String _identifier = element.getIdentifier();
        String _plus = ("Missing JavaDoc link for " + _identifier);
        InputOutput.<String>println(_plus);
        return null;
      }
      _xblockexpression = uri;
    }
    return _xblockexpression;
  }
  
  public String getSourceCodeURI(final JvmIdentifiableElement element) {
    String _xblockexpression = null;
    {
      final String uri = this._gitExtensions.gitLink(element);
      boolean _and = false;
      boolean _and_1 = false;
      boolean _or = false;
      boolean _equals = Objects.equal(uri, null);
      if (_equals) {
        _or = true;
      } else {
        boolean _contains = uri.contains("broken-link");
        _or = _contains;
      }
      if (!_or) {
        _and_1 = false;
      } else {
        String _identifier = element.getIdentifier();
        boolean _notEquals = (!Objects.equal(_identifier, null));
        _and_1 = _notEquals;
      }
      if (!_and_1) {
        _and = false;
      } else {
        final Function1<String,Boolean> _function = new Function1<String,Boolean>() {
          public Boolean apply(final String it) {
            String _identifier = element.getIdentifier();
            return Boolean.valueOf(_identifier.startsWith(it));
          }
        };
        boolean _exists = IterableExtensions.<String>exists(CodeRefs.NO_SOURCE_PACKAGE_PREFIXES, _function);
        boolean _not = (!_exists);
        _and = _not;
      }
      if (_and) {
        String _identifier_1 = element.getIdentifier();
        String _plus = ("Missing source link for " + _identifier_1);
        InputOutput.<String>println(_plus);
        return null;
      }
      _xblockexpression = uri;
    }
    return _xblockexpression;
  }
}

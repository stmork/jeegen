package bootstrap;

import java.util.List;
import java.util.Map;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.xtend.lib.Data;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.util.ToStringHelper;
import org.eclipse.xtext.xdoc.xdoc.AbstractSection;
import org.eclipse.xtext.xdoc.xdoc.Identifiable;

@Data
@SuppressWarnings("all")
public class TargetPathAdapter extends AdapterImpl {
  private final Map<Identifiable,String> _targetPaths = CollectionLiterals.<Identifiable, String>newHashMap();
  
  public Map<Identifiable,String> getTargetPaths() {
    return this._targetPaths;
  }
  
  private final List<AbstractSection> _targetFileRoots = CollectionLiterals.<AbstractSection>newArrayList();
  
  public List<AbstractSection> getTargetFileRoots() {
    return this._targetFileRoots;
  }
  
  public TargetPathAdapter() {
    super();
  }
  
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + ((_targetPaths== null) ? 0 : _targetPaths.hashCode());
    result = prime * result + ((_targetFileRoots== null) ? 0 : _targetFileRoots.hashCode());
    return result;
  }
  
  @Override
  public boolean equals(final Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    if (!super.equals(obj))
      return false;
    TargetPathAdapter other = (TargetPathAdapter) obj;
    if (_targetPaths == null) {
      if (other._targetPaths != null)
        return false;
    } else if (!_targetPaths.equals(other._targetPaths))
      return false;
    if (_targetFileRoots == null) {
      if (other._targetFileRoots != null)
        return false;
    } else if (!_targetFileRoots.equals(other._targetFileRoots))
      return false;
    return true;
  }
  
  @Override
  public String toString() {
    String result = new ToStringHelper().toString(this);
    return result;
  }
}

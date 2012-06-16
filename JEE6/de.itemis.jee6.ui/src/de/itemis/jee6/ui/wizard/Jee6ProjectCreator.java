package de.itemis.jee6.ui.wizard;

import java.util.ArrayList;
import java.util.List;

public class Jee6ProjectCreator extends DslProjectCreator
{
	@Override
	protected List<String> getAllFolders()
	{
		List<String> defaultFolders = new ArrayList<String>(super.getAllFolders());
		defaultFolders.add("WebContent");
		defaultFolders.add("res-gen");
		defaultFolders.add("model");
        return defaultFolders;
    }
}

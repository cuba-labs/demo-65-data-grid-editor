package com.company.demo.web.screens;

import com.haulmont.cuba.gui.components.AbstractWindow;
import com.haulmont.cuba.gui.components.DataGrid;
import com.haulmont.cuba.gui.components.LookupField;
import com.haulmont.cuba.gui.data.CollectionDatasource;
import com.haulmont.cuba.gui.xml.layout.ComponentsFactory;
import com.haulmont.cuba.security.entity.Group;
import com.haulmont.cuba.security.entity.User;

import javax.inject.Inject;
import java.util.Map;
import java.util.UUID;

public class EditorDemo extends AbstractWindow {
    @Inject
    private DataGrid<User> customFieldDataGrid;
    @Inject
    private ComponentsFactory componentsFactory;
    @Inject
    private CollectionDatasource<Group, UUID> groupsDs;
    @Inject
    private DataGrid<User> listenersDataGrid;

    @Override
    public void init(Map<String, Object> params) {
        customFieldDataGrid.getColumnNN("group")
                .setEditorFieldGenerator((datasource, property) -> {
                    LookupField lookupField = componentsFactory.createComponent(LookupField.class);
                    lookupField.setDatasource(datasource, property);
                    lookupField.setOptionsDatasource(groupsDs);
                    return lookupField;
                });

        listenersDataGrid.addEditorPreCommitListener(event ->
                showNotification("Pre Commit"));

        listenersDataGrid.addEditorPostCommitListener(event ->
                showNotification("Post Commit"));

        listenersDataGrid.addEditorCloseListener(event ->
                showNotification("Closed", NotificationType.TRAY));
    }
}
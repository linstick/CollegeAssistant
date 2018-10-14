package com.linstick.collegeassistant.sqlite;

import com.linstick.collegeassistant.beans.Module;

import org.litepal.crud.DataSupport;

import java.util.List;

public class ModuleDaoUtil {

    public static List<Module> findModules() {
        List<Module> result = DataSupport.findAll(Module.class);
        return result;
    }

}

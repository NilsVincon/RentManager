package com.epf.rentmanager.ui.cli;
import com.epf.rentmanager.exception.ServiceException;

public interface Command {
    void execute() throws ServiceException;
}

package com.eds.ctcb.demo.ftp;

import java.io.PrintWriter;
import org.apache.commons.net.ProtocolCommandEvent;
import org.apache.commons.net.ProtocolCommandListener;

/***
 * This is a support class for some of the example programs.  It is
 * a sample implementation of the ProtocolCommandListener interface
 * which just prints out to a specified stream all command/reply traffic.
 * <p>
 ***/

public class PrintCommandListener implements ProtocolCommandListener
{
    private PrintWriter __writer;

    public PrintCommandListener(PrintWriter writer)
    {
        __writer = writer;
    }

    public void protocolCommandSent(ProtocolCommandEvent event)
    {
        __writer.print("-----------[command]:"+event.getMessage());
        __writer.flush();
    }

    public void protocolReplyReceived(ProtocolCommandEvent event)
    {
        __writer.print("-----------[reply]:"+event.getMessage());
        __writer.flush();
    }
}


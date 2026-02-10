package com.hyyperb.sshremote;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import java.io.ByteArrayOutputStream;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class SSHHandler {

    private final SSHHostDetails hostDetails;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    public SSHHandler(SSHHostDetails hostDetails){
        this.hostDetails = hostDetails;
    }


    public String runCommand(String command) {
        Future<String> future = executor.submit(() -> {
            String result;
            try {
                Properties config = new Properties();
                config.put("StrictHostKeyChecking", "no");

                JSch jsch = new JSch();
                Session session = jsch.getSession(hostDetails.username, hostDetails.host, hostDetails.port);
                session.setPassword(hostDetails.password);
                session.setConfig(config);
                session.connect();

                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                ByteArrayOutputStream errorStream = new ByteArrayOutputStream();

                ChannelExec channel = (ChannelExec) session.openChannel("exec");
                channel.setCommand(command);
                channel.setOutputStream(outputStream);
                channel.setExtOutputStream(errorStream);
                channel.connect();

                while (!channel.isClosed()) {
                    Thread.sleep(100);
                }

                channel.disconnect();
                session.disconnect();

                result = outputStream.toString();

                if (result.isEmpty()){
                    result = errorStream.toString();
                }

            } catch (JSchException e) {
                result = "JSchException: " + e.getMessage();
            } catch (InterruptedException e) {
                result = "RuntimeException: " + e.getMessage();
            }
            return result;
        });

        try {
            return future.get();
        } catch (ExecutionException e) {
            return "ExecutionException: " + e.getMessage();
        } catch (InterruptedException e) {
            return "InterruptedException: " + e.getMessage();
        }
    }
}

package de.chrissx.CUtils;

import java.nio.file.Path;
import java.nio.file.Paths;

import de.chrissx.CLibrary.util.Util;

public class P {

	public static final String PLUGIN_NAME = "CUtils";
	public static final String BASE_PATH = Paths.get(Util.CUSTOM_PLUGINS_FOLDER, PLUGIN_NAME).toString();
	public static final Path PLAYER_HOMES = Paths.get(BASE_PATH, "player_homes");
	public static final String PLAYER_HOMES_EXTENTION = ".home";
	public static final Path CMDSPY_BASE = Paths.get(BASE_PATH, "cmdspy");
	public static final Path CMDSPY = Paths.get(BASE_PATH, "cmdspy", "logs"); 
	public static final String CMDSPY_EXTENTION = ".log";
	public static final Path TP_SIGN_LOCATIONS = Paths.get(BASE_PATH, "tp-signs");
	public static final String TP_SIGN_EXTENTION = ".tploc";
	public static final Path CMDSPY_LISTENERS = Paths.get(BASE_PATH, "cmdspy", "listeners");
	public static final String CMDSPY_LISTENERS_EXTENTION = ".cmdspy";
	public static final Path DISABLE_CHAT_LOG = Paths.get(Util.CUSTOM_PLUGINS_FOLDER, "CUtils", "cmdspy", "logs", "dag.f");
	
}
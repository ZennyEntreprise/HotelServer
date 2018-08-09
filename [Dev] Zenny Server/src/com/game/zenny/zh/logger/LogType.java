package com.game.zenny.zh.logger;

public enum LogType {
	INFO(ANSIColor.ANSI_BLUE, "[INFO]"), NORMAL(ANSIColor.ANSI_RESET, "[NORMAL]"), WARNING(ANSIColor.ANSI_YELLOW,
			"[WARNING]"), DANGER(ANSIColor.ANSI_RED, "[DANGER]");

	private String color;
	private String prefix;

	/**
	 * @param color
	 * @param prefix
	 */
	LogType(String color, String prefix) {
		this.color = color;
		this.prefix = prefix;
	}

	/**
	 * @return the color
	 */
	public String getColor() {
		return color;
	}

	/**
	 * @param color
	 *            the color to set
	 */
	public void setColor(String color) {
		this.color = color;
	}

	/**
	 * @return the prefix
	 */
	public String getPrefix() {
		return prefix;
	}

	/**
	 * @param prefix
	 *            the prefix to set
	 */
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	/**
	 * @return the prefix next to the color
	 */
	public String getColorAndPrefix() {
		return color + prefix;
	}
}
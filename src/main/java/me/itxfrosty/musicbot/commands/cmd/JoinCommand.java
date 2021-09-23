package me.itxfrosty.musicbot.commands.cmd;

import me.itxfrosty.musicbot.MusicBot;
import me.itxfrosty.musicbot.audio.guild.GuildAudioManager;
import me.itxfrosty.musicbot.commands.CommandEvent;
import me.itxfrosty.musicbot.commands.SlashCommand;
import net.dv8tion.jda.api.EmbedBuilder;

import java.util.Objects;

public class JoinCommand extends SlashCommand {
	private final GuildAudioManager musicManager;

	public JoinCommand(final MusicBot musicBot) {
		super("join","Makes the bot join the voice channel.","/join",false);
		this.musicManager = musicBot.getGuildAudioManager();
	}

	@Override
	public void execute(CommandEvent event) {
		if (event.getMember() == null || event.getMember().getVoiceState() == null || !event.getMember().getVoiceState().inVoiceChannel() || event.getMember().getVoiceState().getChannel() == null) {
			event.reply(new EmbedBuilder().setDescription("You are not in a voice channel!").build()).queue();
			return;
		}

		if (Objects.requireNonNull(event.getSelfMember().getVoiceState()).inVoiceChannel()) {
			event.reply(new EmbedBuilder().setDescription("The bot is already in a voice channel!").build()).queue();
			return;
		}

		musicManager.joinVoiceChannel(event.getMember().getVoiceState().getChannel(), event.getChannel(), event.getGuild());
		event.reply(new EmbedBuilder().setDescription("Joined `" + event.getMember().getVoiceState().getChannel().getName() + "`").build()).queue();

	}
}

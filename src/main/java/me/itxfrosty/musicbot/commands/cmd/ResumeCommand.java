package me.itxfrosty.musicbot.commands.cmd;

import me.itxfrosty.musicbot.MusicBot;
import me.itxfrosty.musicbot.audio.TrackScheduler;
import me.itxfrosty.musicbot.audio.guild.GuildAudioManager;
import me.itxfrosty.musicbot.commands.CommandEvent;
import me.itxfrosty.musicbot.commands.SlashCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;

public class ResumeCommand extends SlashCommand {
	private final GuildAudioManager musicManager;

	public ResumeCommand(final MusicBot musicBot) {
		super("resume", "Resumes the player.", "/resume",false);
		musicManager = musicBot.getGuildAudioManager();
	}

	@Override
	public void execute(CommandEvent event) {
		final Member member = event.getMember();
		if (member == null || member.getVoiceState() == null || !member.getVoiceState().inVoiceChannel() || member.getVoiceState().getChannel() == null) {
			event.getEvent().getHook().sendMessageEmbeds(new EmbedBuilder().setDescription("Please connect to a voice channel first!").build()).queue();
			return;
		}

		if (musicManager.getGuildAudio(event.getGuild()).getTrackScheduler().getTrackQueue().size() == 0) {
			event.reply(new EmbedBuilder().setDescription("There are no songs playing.").build()).queue();
			return;
		}

		TrackScheduler scheduler = musicManager.getGuildAudio(event.getGuild()).getTrackScheduler();
		scheduler.setPaused(false);
		event.reply(new EmbedBuilder().setDescription(":arrow_forward: Unpaused the Player!").build()).queue();
	}
}

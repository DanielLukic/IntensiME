package net.intensicode.me;

import net.intensicode.core.*;

public final class VoidOnlineSystem implements OnlineAPI
    {
    public final String getUserName()
        {
        return "INTENSICODE";
        }

    public final boolean hasNetworking()
        {
        return false;
        }

    public final boolean isLoggedIn()
        {
        return false;
        }

    public final void loadAchievementIcon( final String aAchievementId, final AchievementIconCallback aCallback )
        {
        aCallback.onAchievementIconFailed( aAchievementId, new RuntimeException( "VoidOnlineSystem" ) );
        }

    public final void progressAchievement( final String aAchievementId, final int aProgressInPercent )
        {
        }

    public final void retrieveHighscores( final LeaderboardCallback aCallback )
        {
        aCallback.onScoresUpdateFailed( new RuntimeException( "VoidOnlineSystem" ) );
        }

    public final void showDashboard()
        {
        }

    public final void showLeaderboard()
        {
        }

    public final void submitScore( final int aScore, final int aLevelNumberStartingAt1, final ScoreSubmissionCallback aCallback )
        {
        aCallback.onScoreSubmissionFailed( new RuntimeException( "VoidOnlineSystem" ) );
        }

    public final void unlockAchievement( final String aAchievementId, final AchievementCallback aCallback )
        {
        aCallback.onAchievementUnlockFailed( aAchievementId, new RuntimeException( "VoidOnlineSystem" ) );
        }
    }

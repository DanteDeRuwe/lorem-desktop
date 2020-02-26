package main.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import main.domain.Location;
import main.domain.Member;
import main.domain.Session;

public class DummySessionProvider {

	private List<Session> sessions = new ArrayList<>();
	private List<Member> organizers;
	private List<Location> locations;

	DummyMemberProvider dmp = new DummyMemberProvider();
	DummyLocationProvider dlp = new DummyLocationProvider();

	public DummySessionProvider(int number) {
		for (int i = 0; i < number; i++) {
			addRandomSession();
		}
	}

	private void addRandomSession() {
		LocalDateTime[] randomStartStop = Util.randomStartStopThisYear();
		Member organizer = dmp.getRandomOrganizer();
		Location randomLocation = dlp.getRandomLocation();

		sessions.add(new Session(organizer, getRandomTitle(), getRandomSpeaker(), randomStartStop[0],
				randomStartStop[1], randomLocation));
	}

	private String getRandomTitle() {
		String titles = "LOREM Smackdown!,5 Ways LOREM Will Help You Get More Business,3 Ways Create Better LOREM With The Help Of Your Dog,3 Tips About LOREM You Can't Afford To Miss,Double Your Profit With These 5 Tips on LOREM,Want To Step Up Your LOREM? You Need To Read This First,Why I Hate LOREM,9 Ways LOREM Can Make You Invincible,Boost Your LOREM With These Tips,Why LOREM Is The Only Skill You Really Need,5 Secrets: How To Use LOREM To Create A Successful Business(Product),The Truth About LOREM In 3 Minutes,How To Earn $398/Day Using LOREM,Using 7 LOREM Strategies Like The Pros,Why Some People Almost Always Make/Save Money With LOREM,Best LOREM Android/iPhone Apps,Proof That LOREM Is Exactly What You Are Looking For,Avoid The Top 10 Mistakes Made By Beginning LOREM,How To Learn LOREM,Never Lose Your LOREM Again,How We Improved Our LOREM In One Week(Month, Day),3 Ways Twitter Destroyed My LOREM Without Me Noticing,Here Is A Quick Cure For LOREM,Top 10 Tips With LOREM,How To Buy (A) LOREM On A Tight Budget,10 Things You Have In Common With LOREM,LOREM: This Is What Professionals Do,3 Ways To Have (A) More Appealing LOREM,How To Get (A) Fabulous LOREM On A Tight Budget,Marriage And LOREM Have More In Common Than You Think,Ho To (Do) LOREM Without Leaving Your Office(House).,3 Ways You Can Reinvent LOREM Without Looking Like An Amateur,Fear? Not If You Use LOREM The Right Way!,Death, LOREM And Taxes,Should Fixing LOREM Take 60 Steps?,You Can Thank Us Later - 3 Reasons To Stop Thinking About LOREM,14 Days To A Better LOREM,What Can Instagramm Teach You About LOREM,Why Most People Will Never Be Great At LOREM,Lies And Damn Lies About LOREM,Why You Really Need (A) LOREM,LOREM: What A Mistake!,10 Unforgivable Sins Of LOREM,The Secrets To Finding World Class Tools For Your LOREM Quickly,3 Ways To Master LOREM Without Breaking A Sweat,Wondering How To Make Your LOREM Rock? Read This!,Stop Wasting Time And Start LOREM,Who Else Wants To Know The Mystery Behind LOREM?,Some People Excel At LOREM And Some Don't - Which One Are You?,Are You Embarrassed By Your LOREM Skills? Here's What To Do,Find Out Now, What Should You Do For Fast LOREM?,Learn Exactly How We Made LOREM Last Month,Why My LOREM Is Better Than Yours,Winning Tactics For LOREM,5 Incredibly Useful LOREM Tips For Small Businesses,The Best Way To LOREM,5 Ways Of LOREM That Can Drive You Bankrupt - Fast!,5 Problems Everyone Has With LOREM – How To Solved Them,The LOREM That Wins Customers,Succeed With LOREM In 24 Hours,Short Story: The Truth About LOREM,Cracking The LOREM Code,5 Easy Ways You Can Turn LOREM Into Success,Want More Money? Start LOREM,Got Stuck? Try These Tips To Streamline Your LOREM,Are You Making These LOREM Mistakes?,The Lazy Way To LOREM,3 Simple Tips For Using LOREM To Get Ahead Your Competition,11 Methods Of LOREM Domination,5 Best Ways To Sell LOREM,A Surprising Tool To Help You LOREM,5 Brilliant Ways To Teach Your Audience About LOREM,LOREM Shortcuts - The Easy Way,LOREM Adventures,Improve(Increase) Your LOREM In 3 Days,LOREM And The Chuck Norris Effect,You Don't Have To Be A Big Corporation To Start LOREM,Fast-Track Your LOREM,Apply These 5 Secret Techniques To Improve LOREM,Here Is What You Should Do For Your LOREM,There’s Big Money In LOREM,Savvy|Smart|Sexy People Do LOREM :),The Ultimate Deal On LOREM,Now You Can Buy An App That is Really Made For LOREM,What Make LOREM Don't Want You To Know,27 Ways To Improve LOREM,The Hidden Mystery Behind LOREM,Little Known Ways To Rid Yourself Of LOREM,Sexy LOREM,How To Deal With(A) Very Bad LOREM,5 Sexy Ways To Improve Your LOREM,LOREM: The Samurai Way,10 Warning Signs Of Your LOREM Demise,At Last, The Secret To LOREM Is Revealed,You Will Thank Us - 10 Tips About LOREM You Need To Know,How I Improved My LOREM In One Easy Lesson,How To Improve At LOREM In 60 Minutes,If LOREM Is So Terrible, Why Don't Statistics Show It?,What You Can Learn From Bill Gates About LOREM,How You Can (Do) LOREM In 24 Hours Or Less For Free,Why LOREM Succeeds,Could This Report Be The Definitive Answer To Your LOREM?,22 Tips To Start Building A LOREM You Always Wanted,How To Take The Headache Out Of LOREM,Is LOREM Worth [$] To You?,At Last, The Secret To LOREM Is Revealed,Beware The LOREM Scam,Get Rid of LOREM For Good,Clear And Unbiased Facts About LOREM (Without All the Hype),No More Mistakes With LOREM,Why You Never See LOREM That Actually Works,LOREM Strategies For Beginners,3 LOREM Secrets You Never Knew,How To Win Clients And Influence Markets with LOREM,The Ugly Truth About LOREM,How To Save Money with LOREM?,Warning: These 9 Mistakes Will Destroy Your LOREM,7 and a Half Very Simple Things You Can Do To Save LOREM,Does LOREM Sometimes Make You Feel Stupid?,The Secret Of LOREM,Top 25 Quotes On LOREM,Who Else Wants To Be Successful With LOREM,LOREM: Do You Really Need It? This Will Help You Decide!,Your Key To Success: LOREM,5 Romantic LOREM Ideas,LOREM Is Bound To Make An Impact In Your Business,How To Make Your LOREM Look Like A Mill";
		return Util.randomChoice(titles.split(","));
	}

	private String getRandomSpeaker() {
		// just use an organizer name
		Member organizer = dmp.getRandomOrganizer();
		return organizer.getFullName();
	}

	public List<Session> getSessions() {
		return sessions;
	}

}

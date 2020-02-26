package main.services;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;

import main.domain.Member;
import main.domain.MemberType;

public class DummyMemberProvider {

	private List<Member> organizers = new ArrayList<>();

	public DummyMemberProvider() {
		seedOrganizers();
	}

	private void seedOrganizers() {
		organizers.add(new Member("JohnDoe", "John", "Doe", MemberType.HEADADMIN));

		String names = "Liam Spitaels,Arne DeSchrijver,Sam Brysbaert,Dante DeRuwe,Alleen Friedel,Nga Freel,Jenelle Holcomb,Shanda Davalos,Hai Dunkerson,Gemma Knoll,Amparo Mattinson,Curtis Yarbrough,Vaughn Kopec,Brigida Italiano,Penelope Dantzler,Tanja Ketron,Alejandra Louviere,Hyman Addario,Roxy Remus,Kia Brody,Clara Devlin,Ardell Wiggin,Twanda Pettiway,Aimee Mcglamery,Wendi Saam,Nikki Hatchett,Thaddeus Phillips,Cordell Shott,Brinda Bugg,Raphael Lonergan,Raymond Kellems,Sherrie Householder,Janessa Points,Dayle Racanelli,Thora Render,Ralph Coulombe,Stephenie Delcastillo,Bennie Quick,Lavenia Tompson,Theresia Unruh,Fernande Newhouse,Shirlene Treece,Coretta Carlock,Shantay Giannone,Albertina Nutting,Denis Slick,Dylan Levine,Ardelle Faulcon,Mari Neufeld,Arnoldo Otto,Jenette Hyder,Tereasa Steidl,Noah Mcmorran,Tana Orvis";
		Stream.of(names.split(",")).forEach(name -> {
			String[] nameArr = name.split(" ");
			String firstName = nameArr[0];
			String lastName = nameArr[1];
			organizers.add(new Member(firstName + lastName, firstName, lastName, MemberType.HEADADMIN));
		});
	}

	public List<Member> getOrganizers() {
		return organizers;
	}

	public Member getRandomOrganizer() {
		int i = ThreadLocalRandom.current().nextInt(0, organizers.size());
		return organizers.get(i);
	}

}

package arcosin.genetic_algo.tsp;

import java.util.HashSet;
import java.util.Set;

public class GeneticAlgo
{
	//How often a child tour will mutate.
	public static final double MUTATION_RATE = 0.015;
	
	//Size of random tournaments.
	public static final int TOURNAMENT_SIZE = 10;
	
	//Save the best of the original population during evolution.
	public static final boolean ELITISM = true;

	
	
	
	public static Population evolve(Population pop)
	{
		Population evolvedPopulation = new Population(pop.size(), false);
		int offset = 0;
		
		if(ELITISM)
		{
			evolvedPopulation.setTour(0, pop.getFittest());
			offset++;
		}
		
		for(int i = offset; i < evolvedPopulation.size(); i++)
		{
			Tour parent1 = tournamentSelection(pop);
			Tour parent2 = tournamentSelection(pop);
			Tour child = crossover(parent1, parent2);
			mutate(child);
			evolvedPopulation.setTour(i, child);
		}
		
		return evolvedPopulation;
	}
	
	
	
	public static Tour crossover(Tour parent1, Tour parent2)
	{
		Tour child = new Tour(false);
		int start = (int) (Math.random() * parent1.size());
		int end = (int) (Math.random() * parent1.size());
		Set<Integer> added = new HashSet<Integer>();
		int q = 0;
		
		//Switch start and end if end is before start.
		if(start > end)
		{
			int temp = start;
			start = end;
			end = temp;
		}
		
		//Add block from parent 1.
		for(int i = start; i < end; i++)
		{
			City temp = parent1.getCity(i);
			added.add(temp.getID());
			child.setCity(i, temp);
		}
		
		//Add prefix from parent 2.
		for(int i = 0; i < start && q < parent2.size(); i++, q++)
		{
			City temp = parent2.getCity(q);
			
			if(!added.contains((Integer) temp.getID()))
			{
				child.setCity(i, temp);
			}
			else
			{
				i--;
			}
		}
		
		//Add suffix from parent 2.
		for(int i = end; i < child.size() && q < parent2.size(); i++, q++)
		{
			City temp = parent2.getCity(q);
			
			if(!added.contains((Integer) temp.getID()))
			{
				child.setCity(i, temp);
			}
			else
			{
				i--;
			}
		}
		
		return child;
	}
	
	
	
	private static void mutate(Tour tour)
	{
		for(int i = 0; i < tour.size(); i++)
		{
			if(Math.random() < MUTATION_RATE)
			{
				int switcher = (int) (tour.size() * Math.random());
				
				City first = tour.getCity(i);
				City second = tour.getCity(switcher);
				
				tour.setCity(i, second);
				tour.setCity(switcher, first);
			}
		}
	}
	
	
	
	private static Tour tournamentSelection(Population pop)
	{
		Population tournament = new Population(TOURNAMENT_SIZE, false);
		
		for(int i = 0; i < TOURNAMENT_SIZE; i++)
		{
			int randomId = (int) (Math.random() * pop.size());
			tournament.setTour(i, pop.getTour(randomId));
		}
		
		return tournament.getFittest();
	}
}

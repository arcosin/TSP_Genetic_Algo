package arcosin.genetic_algo.tsp;

import java.util.ArrayList;
import java.util.Arrays;


public class Population
{
	private ArrayList<Tour> tours;
	

	
	public Population(int size, boolean init)
	{
		//tours = new ArrayList<Tour>(size);
		tours = new ArrayList<Tour>(Arrays.asList(new Tour[size]));
		
		if(init)
		{
			for(int i = 0; i < size; i++)
			{
				tours.set(i, new Tour(true));
			}
		}
	}
	
	
	
	public void setTour(int index, Tour tour)
	{
		tours.set(index, tour);
	}
	
	
	
	public Tour getTour(int index)
	{
		return tours.get(index);
	}
	
	
	
	public int size()
	{
		return tours.size();
	}
	
	
	
	public Tour getFittest()
	{
		Tour best = tours.get(0);
		
		for(int i = 1; i < tours.size(); i++)
		{
			Tour temp = tours.get(i);
			if(temp.getFitness() > best.getFitness())   best = temp;
		}
		
		return best;
	}
	
	
	
	@Override
	public String toString()
	{
		String str = "--population--\n";
		
		for(Tour tour : tours)
		{
			str = str + "   " + tour.toString() + "\n";
		}
		
		return str;
	}
}

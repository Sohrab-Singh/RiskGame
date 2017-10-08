package com.game.risk.view;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;

import com.game.risk.core.parser.MapFileParser;
import com.game.risk.core.parser.MapFileWriter;
import com.game.risk.model.Country;

/**
 * Editor View Class
 * 
 * @author Sarthak
 */
public class MapEditorView {
	private MapFileWriter mapFileWriter;
	private MapFileParser mapFileParser;

	/**
	 * Constructor
	 * 
	 * @param mapFileParser reference to the Map File Parser object
	 * @param mapFileWriter reference to the Map File Writer object
	 */
	public MapEditorView(MapFileParser mapFileParser, MapFileWriter mapFileWriter) {
		this.mapFileParser = mapFileParser;
		this.mapFileWriter = mapFileWriter;
	}
	
	/**
	 * Method to print the adjacency Matrix representation and edit the map
	 */
	public void readMapEditor() {
		Iterator iterator =  mapFileParser.getCountriesGraph().getAdjListHashMap().entrySet().iterator();
		while(iterator.hasNext()) {
			Map.Entry<Country, LinkedList<Country>> pair = (Map.Entry<Country, LinkedList<Country>>) iterator.next(); 
			StringBuilder builder = new StringBuilder();
			System.out.print(builder.append(pair.getKey().getCountryName()).append(" "));
			for(Country adjCountry : pair.getValue()) {
				System.out.print(builder.append(adjCountry.getCountryName()).append(" "));
			}
			System.out.println();
		}
		final Scanner sc = new Scanner(System.in);
		while(true) {
			System.out.println("1. Add a Country\n2. Delete a country\n3. Add an Edge\n4. Delete an Edge\n5. Add a Continent\n6. Delete a Continent\n7. Exit");
			int choice = sc.nextInt();
			if(choice == 7) break;
		}
	}
}

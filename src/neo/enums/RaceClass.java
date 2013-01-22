/**
 * 
 */
package neo.enums;

import org.neo4j.graphdb.RelationshipType;

/**
 * @author Miriam Martin
 *
 */
public enum RaceClass implements RelationshipType {
	GROUP1, GROUP2, GROUP3,
	LISTED,
	CLASS2, CLASS3, CLASS4, CLASS5, CLASS6,
	CLAIMER, SELLER,
	MAIDEN4, MAIDEN5,
	CONDITIONS;
}

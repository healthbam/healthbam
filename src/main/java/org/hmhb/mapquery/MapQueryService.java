package org.hmhb.mapquery;

/**
 * Logic layer for {@link MapQuery} objects.
 */
public interface MapQueryService {

    /**
     * Searches for programs matching the provided criteria.
     * @param mapQuery {@link MapQuery} containing {@link MapQuerySearch} criteria.
     * @return {@link MapQuery} with filled in {@link MapQueryResult} object.
     */
    MapQuery search(MapQuery mapQuery);

}

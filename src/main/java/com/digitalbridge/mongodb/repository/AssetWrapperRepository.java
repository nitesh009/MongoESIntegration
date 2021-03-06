package com.digitalbridge.mongodb.repository;

import java.util.List;
import java.util.concurrent.Future;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.geo.Circle;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.access.prepost.PreAuthorize;

import com.digitalbridge.domain.AssetWrapper;

/**
 * <p>
 * AssetWrapperRepository interface.
 * </p>
 *
 * @author rajakolli
 * @version 1: 0
 */
@RepositoryRestResource(collectionResourceRel = "assets", path = "assetwrapper")
@PreAuthorize("hasRole('ROLE_USER')")
public interface AssetWrapperRepository extends MongoRepository<AssetWrapper, String> {

    /**
     * <p>
     * findAll.
     * </p>
     *
     * @param pageable a {@link org.springframework.data.domain.Pageable} object.
     * @return a {@link org.springframework.data.domain.Page} object.
     */
    @Async
    @RestResource(path = "find")
    Page<AssetWrapper> findAll(Pageable pageable);

    /**
     * <p>
     * findByAddressLocationNull.
     * </p>
     *
     * @return a {@link java.util.List} object.
     */
    @Async
    Future<AssetWrapper> findByLocationNull();

    /**
     * <p>findByLastModifiedByIsNull.</p>
     *
     * @return a {@link java.util.List} object.
     */
    List<AssetWrapper> findByLastModifiedByIsNull();
    
    void findByCuisineIsInAndIgnoreCase(List<String> cuisine);

    /**
     * <p>
     * findByGradesScoreGreaterThanEqual.
     * </p>
     *
     * @param score a {@link java.lang.Integer} object.
     * @return a {@link java.util.List} object.
     */
    List<AssetWrapper> findByNotesScoreGreaterThanEqual(@Param("score") Integer score);

    /**
     * <p>
     * findByAddressLocationWithin.
     * </p>
     *
     * @param circle a {@link org.springframework.data.geo.Circle} object.
     * @return a {@link org.springframework.data.geo.GeoResults} object.
     */
    GeoResults<AssetWrapper> findByLocationWithin(Circle circle);

    /**
     * <p>
     * findByCuisine.
     * </p>
     *
     * @param cuisine a {@link java.lang.String} object.
     * @return a {@link java.util.List} object.
     */
    List<AssetWrapper> findByCuisine(@Param("cuisine") String cuisine);

    /**
     * <p>
     * queryFirst10ByAssetName.
     * </p>
     *
     * @param assetName a {@link java.lang.String} object.
     * @param pageable a {@link org.springframework.data.domain.Pageable} object.
     * @return a {@link org.springframework.data.domain.Page} object.
     */
    Page<AssetWrapper> queryFirst10ByAssetName(@Param("assetName") String assetName,
            Pageable pageable);

    /**
     * <p>
     * findTop3ByAssetName.
     * </p>
     *
     * @param assetName a {@link java.lang.String} object.
     * @param pageable a {@link org.springframework.data.domain.Pageable} object.
     * @return a {@link org.springframework.data.domain.Slice} object.
     */
    Slice<AssetWrapper> findTop3ByAssetName(@Param("assetName") String assetName,
            Pageable pageable);

    /**
     * <p>
     * findByIdIn.
     * </p>
     *
     * @param assetIds a {@link java.util.List} object.
     * @param pageable a {@link org.springframework.data.domain.Pageable} object.
     * @return a {@link org.springframework.data.domain.Page} object.
     */
    Page<AssetWrapper> findByIdIn(@Param("assetIds") List<String> assetIds,
            Pageable pageable);

    /**
     * <p>
     * findByAddressLocationNearAndIdIn.
     * </p>
     *
     * @param point a {@link org.springframework.data.geo.Point} object.
     * @param distance a {@link org.springframework.data.geo.Distance} object.
     * @param assetIds a {@link java.util.List} object.
     * @param pageable a {@link org.springframework.data.domain.Pageable} object.
     * @return a {@link org.springframework.data.domain.Page} object.
     */
    Page<AssetWrapper> findByLocationNearAndIdIn(@Param("point") Point point,
            @Param("distance") Distance distance,
            @Param("assetIds") List<String> assetIds, Pageable pageable);

    /**
     * <p>findByLocationNear.</p>
     *
     * @param point a {@link org.springframework.data.geo.Point} object.
     * @param distance a {@link org.springframework.data.geo.Distance} object.
     * @param pageable a {@link org.springframework.data.domain.Pageable} object.
     * @return a {@link org.springframework.data.domain.Page} object.
     */
    Page<AssetWrapper> findByLocationNear(@Param("point") Point point,
            @Param("distance") Distance distance, Pageable pageable);

}

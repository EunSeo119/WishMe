package com.wishme.schoolLetter.asset.repository;

import com.wishme.schoolLetter.asset.domain.Asset;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AssetRepository extends JpaRepository<Asset, Long> {

    Optional<Asset> findByAssetSeq(Long assetSeq);

    List<Asset> findByType(char type);

}

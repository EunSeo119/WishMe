package com.wishme.myLetter.myLetter.repository;

import com.wishme.myLetter.asset.domain.Asset;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AssetRepository extends JpaRepository<Asset, Long> {

    List<Asset> findAllByTypeOrderByAssetSeq(char type);

    Optional<Asset> findByAssetSeqAndType(Long assetSeq, char type);
}

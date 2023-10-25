package com.wishme.myLetter.myLetter.repository;

import com.wishme.myLetter.asset.domain.Asset;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssetRepository extends JpaRepository<Asset, Long> {

    List<Asset> findAllByOrderByAssetSeq();
}

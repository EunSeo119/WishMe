import React, { useState, useEffect } from 'react'
import { useParams } from 'react-router-dom'
import axios from 'axios'
import style from './schoolLetterAssetList.module.css'
import { useNavigate } from 'react-router-dom'
import { IoIosArrowBack, IoIosArrowForward } from 'react-icons/io'

const SchooLetterAssetList = () => {
  const [schoolAssetList, setSchoolAssetList] = useState([])
  const [page, setPage] = useState(1)
  const [totalPage, setTotalPage] = useState(1)
  const [selectAsset, setSelectAsset] = useState()
  const [selectedItemIndex, setSelectedItemIndex] = useState(null)

  const { schoolUuid } = useParams()
  const navigate = useNavigate()

  const assetClick = (assetId) => {
    setSelectAsset(assetId)
  }

  const letterWriteNextClick = () => {
    if (selectAsset) {
      navigate(`/schoolLetterWritePage/${selectAsset}/${schoolUuid}`)
    } else {
      alert('문구를 선택해주세요!')
    }
  }
  const goPre = () => {
    navigate(`/school/${schoolUuid}`)
  }

  const changePage = (newPage) => {
    console.log(totalPage)
    if (newPage >= 1 && newPage <= totalPage) {
      setPage(newPage)
    }
  }

  useEffect(() => {
    axios
      .get(`https://wishme.co.kr/api/school/letter/assets`)
      // .get(`http://localhost:8082/api/school/letter/assets`)
      .then((response) => {
        const data = response.data
        console.log(data)
        setSchoolAssetList(data.schoolAssertList)
        setTotalPage(Math.ceil(data.schoolAssertList.length / 12))
        console.log(schoolAssetList)
        console.log(totalPage)
      })
      .catch((error) => {
        console.error('API 요청 중 오류 발생:', error)
      })
  }, [schoolUuid])

  return (
    <div className={style.body}>
      <div className={style.schoolName} onClick={() => goPre()}>
        <IoIosArrowBack className={style.icon} />
        이전으로
      </div>
      <div className={style.title}>문구를 선택해주세요</div>
      <div className={style.gridContainer}>
        <div
          className={`${style.arrowIcon} ${
            page === 1 ? style.disabledArrow : ''
          }`}
          onClick={() => {
            if (page > 1) {
              changePage(page - 1)
            }
          }}
        >
          <IoIosArrowBack />
        </div>

        <div className={style.gridItemContainer}>
          {schoolAssetList
            .slice((page - 1) * 12, (page - 1) * 12 + 12)
            .map((asset, index) => (
              <div
                key={index}
                className={`${style.gridItem} ${
                  index === selectedItemIndex ? style.selectAsset : ''
                }`}
                onClick={() => {
                  assetClick(asset.assetSeq)
                  setSelectedItemIndex(index)
                }}
              >
                <img
                  crossOrigin="anonymous"
                  src={asset.assetImg}
                  alt={`Asset ${index}`}
                />
              </div>
            ))}
          {page > schoolAssetList.length / 12 &&
            Array.from({ length: 12 - (schoolAssetList.length % 12) }).map(
              (_, index) => (
                <div
                  key={schoolAssetList.length + index}
                  className={style.gridItem}
                ></div>
              )
            )}
        </div>
        <div
          className={`${style.arrowIcon} ${
            page === totalPage ? style.disabledArrow : ''
          }`}
          onClick={() => changePage(page + 1)}
        >
          <IoIosArrowForward />
        </div>
      </div>
      <div className={style.btn}>
        <div
          className={style.mySchoolBtn}
          onClick={() => letterWriteNextClick()}
        >
          다음
        </div>
      </div>
    </div>
  )
}

export default SchooLetterAssetList

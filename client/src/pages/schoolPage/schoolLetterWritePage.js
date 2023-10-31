import React, { useState, useRef, useEffect } from 'react'
import { useParams } from 'react-router-dom'
import axios from 'axios'
import style from './schoolLetterWritePage.module.css'
import { useNavigate } from 'react-router-dom'
import { IoIosArrowBack, IoIosArrowForward } from 'react-icons/io'

const SchooLetterWritePage = () => {
  const [schoolAssetList, setSchoolAssetList] = useState([])
  const [page, setPage] = useState(1)
  const [totalPage, setTotalPage] = useState(1)
  const [selectAsset, setSelectAsset] = useState(1)
  const [nickname, setNickname] = useState('')
  const [content, setContent] = useState('')
  const { schoolUuid, assetId } = useParams()

  const navigate = useNavigate()

  const clickWriteLetter = () => {
    if (nickname && content) {
      axios
        .post(`https://wishme.co.kr/api/school/letter/write/uuid`, {
          uuid: schoolUuid,
          assetSeq: assetId,
          content: content,
          nickname: nickname
        })
        .then((response) => {
          const data = response.data
          // alert('응원남기기 완료!')
          navigate(`/school/${schoolUuid}`)
        })
        .catch((error) => {
          console.error('API 요청 중 오류 발생:', error)
          console.log(error)
        })
    } else {
      alert('닉네임과 내용을 입력해주세요')
    }
  }

  const assetClick = (assetId) => {
    setSelectAsset(assetId)
  }

  const letterWriteNextClick = () => {
    navigate(`/schoolLetterDetail/${selectAsset}`)
  }
  const goPre = (assetId) => {
    setSelectAsset(assetId)
  }

  const changePage = (newPage) => {
    console.log(totalPage)
    if (newPage >= 1 && newPage <= totalPage) {
      setPage(newPage)
    }
  }

  const handleNicknameChange = (event) => {
    setNickname(event.target.value)
  }

  //   useEffect(() => {
  //     axios
  //       .get(`/api/school/letter/assets`)
  //       .then((response) => {
  //         const data = response.data
  //         console.log(data)
  //         setSchoolAssetList(data.schoolAssertList)
  //         setTotalPage(Math.ceil(data.schoolAssertList.length / 12))
  //         console.log(schoolAssetList)
  //         console.log(totalPage)
  //       })
  //       .catch((error) => {
  //         console.error('API 요청 중 오류 발생:', error)
  //       })
  //   }, [assetId])

  return (
    <div className={style.body}>
      <div className={style.schoolName} onClick={() => goPre()}>
        <IoIosArrowBack className={style.icon} />
        이전으로
      </div>
      <div className={style.title}>응원의 말을 남겨주세요!</div>
      <div className={style.nickname}>
        <input
          className={style.nicknameInput}
          type="text"
          id="nickname"
          value={nickname}
          placeholder="닉네임을 입력해주세요."
          onChange={handleNicknameChange}
        />
      </div>
      <div className={style.letterImg}>
        <img
          crossOrigin="anonymous"
          src="https://wishme-bichnali.s3.ap-northeast-2.amazonaws.com/letter/clovaLetter.png"
        />
        <textarea
          className={style.contentTextarea}
          placeholder="응원의 글을 적어주세요."
          value={content}
          onChange={(e) => setContent(e.target.value)}
        />{' '}
      </div>
      <div className={style.btn}>
        <div className={style.mySchoolBtn} onClick={() => clickWriteLetter()}>
          응원 남기기
        </div>
      </div>
    </div>
  )
}

export default SchooLetterWritePage

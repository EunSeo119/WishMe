import React, { useState, useRef, useEffect } from 'react'
import { useParams } from 'react-router-dom'
import axios from 'axios'
import style from './developerLetterDetail.module.css'
import { useNavigate } from 'react-router-dom'
import { IoIosArrowBack, IoIosArrowForward, IoIosAlert } from 'react-icons/io'
import tokenHttp from '../../apis/tokenHttp'

const DeveloperLetterDetail = () => {
  const { page, letterId } = useParams()
  const [nickname, setNickname] = useState('')
  const [content, setContent] = useState('')
  const [isMine, setIsMine] = useState(false)

  const SERVER_URL = process.env.REACT_APP_SERVER_URL

  const navigate = useNavigate()

  useEffect(() => {
    const AccessToken = localStorage.getItem('AccessToken')
    const RefreshToken = localStorage.getItem('RefreshToken')

    if (AccessToken) {
      // AccessToken이 있으면 내 책상 페이지로 이동
      tokenHttp({
        method: 'get',
        url: `${SERVER_URL}/api/developer/letter/one/${letterId}`,
        headers: {
          Authorization: `Bearer ${AccessToken}`,
          RefreshToken: `${RefreshToken}`
        }
      })
        .then((response) => {
          const data = response.data
          setContent(data.content)
          setNickname(data.nickname)
          setIsMine(data.isMine)
          console.log(isMine)
        })
        .catch((error) => {
          // console.error('API 요청 중 오류 발생:', error)
        })
    }
  }, [content])

  const goPre = () => {
    navigate(`/developer/${page}`)
    // navigate(-1)
  }

  const writeReplyLetter = () => {
    navigate(`/replyWritePage/${letterId}`)
  }

  return (
    <div className={style.body}>
      <div className={style.schoolName} onClick={() => goPre()}>
        <IoIosArrowBack className={style.icon} />
        이전으로
      </div>
      <div className={style.title}>응원의 편지를 확인하세요!</div>
      <div className={style.letterImgBack}>
        <img
          crossOrigin="anonymous"
          src="https://wishme-bichnali.s3.ap-northeast-2.amazonaws.com/letter/clovaLetter.png"
        />
        {/* 여기가 편지 내용 */}
        <div className={style.letter}>
          <div className={style.to}>
            <text className={style.letterPrefix}>To. 빛나리</text>
          </div>
          <div className={style.content}>
            <textarea
              className={style.contentTextarea}
              value={content}
              onChange={(e) => setContent(e.target.value)}
            />{' '}
          </div>
          <div className={style.from}>
            <text className={style.letterSurfix}>From. {nickname}</text>
          </div>{' '}
        </div>
      </div>
      <div className={style.btn}>
        {isMine ? (
          <>
            <div className={style.replyBtn} onClick={() => writeReplyLetter()}>
              답장하기
            </div>
          </>
        ) : (
          <></>
        )}
        <div className={style.mySchoolBtn} onClick={() => goPre()}>
          닫기
        </div>
      </div>
    </div>
  )
}

export default DeveloperLetterDetail

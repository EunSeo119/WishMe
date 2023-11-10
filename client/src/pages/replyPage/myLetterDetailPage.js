import React, { useState, useEffect } from 'react'
import { useParams } from 'react-router-dom'
import axios from 'axios'
import style from './myLetterDetailPage.module.css'
import { useNavigate } from 'react-router-dom'
import { IoIosArrowBack } from 'react-icons/io'
import tokenHttp from '../../apis/tokenHttp'

const MyLetterDetailPage = () => {
  const { letterId } = useParams()
  const [toUserNickname, setToUserNickname] = useState('')
  const [fromUserNickname, setFromUserNickname] = useState('')
  const [content, setContent] = useState('')
  const [isMine, setIsMine] = useState(false)

  const MYLETTER_SERVER = process.env.REACT_APP_MYLETTER_SERVER

  const navigate = useNavigate()

  useEffect(() => {
    const AccessToken = localStorage.getItem('AccessToken')
    const RefreshToken = localStorage.getItem('RefreshToken')

    if (AccessToken) {
      // AccessToken이 있으면 내 책상 페이지로 이동
      tokenHttp({
        method: 'get',
        url: `${MYLETTER_SERVER}/api/developer/letter/detail/${letterId}`,
        headers: {
          Authorization: `Bearer ${AccessToken}`,
          RefreshToken: `${RefreshToken}`
        }
      })
        .then((response) => {
          const data = response.data
          setContent(data.content)
          setToUserNickname(data.toUserNickname)
          setFromUserNickname(data.fromUserNickname)
          setIsMine(data.isMine)
        })
        .catch((error) => {
        })
    } else {
      axios({
        method: 'get',
        url: `${MYLETTER_SERVER}/api/developer/letter/one/${letterId}`
      })
        .then((response) => {
          const data = response.data
          setContent(data.content)
          setToUserNickname(data.toUserNickname)
          setFromUserNickname(data.fromUserNickname)
          setIsMine(data.isMine)
        })
        .catch((error) => {
        })
    }
  }, [content])

  const goPre = () => {
    navigate(-1)
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
        <div className={style.letter}>
          <div className={style.to}>
            <text className={style.letterPrefix}>To. {toUserNickname}</text>
          </div>
          <div className={style.content}>
            <textarea
              className={style.contentTextarea}
              value={content}
              onChange={(e) => setContent(e.target.value)}
            />{' '}
          </div>
          <div className={style.from}>
            <text className={style.letterSurfix}>From. {fromUserNickname}</text>
          </div>{' '}
        </div>
      </div>
      <div className={style.btn}>
        <div className={style.mySchoolBtn} onClick={() => goPre()}>
          닫기
        </div>
      </div>
    </div>
  )
}

export default MyLetterDetailPage

import React, { useState, useRef, useEffect } from 'react'
import { useParams } from 'react-router-dom'
import axios from 'axios'
import style from './replyDetailPage.module.css'
import { useNavigate } from 'react-router-dom'
import { IoIosArrowBack, IoIosArrowForward, IoIosAlert } from 'react-icons/io'
import { PiSirenLight } from 'react-icons/pi'

const ReplyDetailPage = () => {
  const { replyId } = useParams()
  const [toNickname, setToNickname] = useState('')
  const [fromNickname, setFromNickname] = useState('')

  const [content, setContent] = useState('')
  const SERVER_URL = process.env.REACT_APP_SERVER_URL
  const navigate = useNavigate()

  //편지 배경
  const imageLetter = [
    {
      id: 'Y',
      url: 'https://wishme-bichnali.s3.ap-northeast-2.amazonaws.com/letter/yellowLetter.png'
    },
    {
      id: 'P',
      url: 'https://wishme-bichnali.s3.ap-northeast-2.amazonaws.com/letter/pinkLetter.png'
    },
    {
      id: 'B',
      url: 'https://wishme-bichnali.s3.ap-northeast-2.amazonaws.com/letter/blueLetter.png'
    }
  ]

  useEffect(() => {
    const AccessToken = localStorage.getItem('AccessToken') // 토큰 값을 가져오는 코드
    const RefreshToken = localStorage.getItem('RefreshToken')
    const headers = {}
    if (AccessToken) {
      headers.Authorization = `Bearer ${AccessToken}`
      headers.RefreshToken = `${RefreshToken}`
    }
    axios({
      method: 'get',
      url: `${SERVER_URL}/api/my/reply/detail/${replyId}`,
      headers
    })
      .then((response) => {
        const data = response.data
        console.log(data)
        setToNickname(data.toUserNickname)
        setContent(data.content)
        setFromNickname(data.fromUserNickname)
      })
      .catch((error) => {
        // console.error('API 요청 중 오류 발생:', error)
      })
  }, [content])

  //이전으로
  const goPre = () => {
    navigate(-1)
  }

  const handleReportClick = () => {
    // navigate(`/school/${schoolUuid}`)
    navigate(-1)
  }

  return (
    <div className={style.body}>
      <div className={style.schoolName} onClick={() => goPre()}>
        <IoIosArrowBack className={style.icon} />
        이전으로
      </div>
      <div className={style.title}>답장을 확인하세요!</div>
      <div className={style.letterImgBack}>
        <img
          crossOrigin="anonymous"
          src="https://wishme-bichnali.s3.ap-northeast-2.amazonaws.com/letter/clovaLetter.png"
        />
        {/* 여기가 편지 내용 */}
        <div className={style.letter}>
          <div className={style.to}>
            <text className={style.letterPrefix}>To. {toNickname}</text>
          </div>
          <div className={style.content}>
            <textarea
              className={style.contentTextarea}
              value={content}
              onChange={(e) => setContent(e.target.value)}
            />{' '}
          </div>
          <div className={style.from}>
            <text className={style.letterSurfix}>From. {fromNickname}</text>
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

export default ReplyDetailPage

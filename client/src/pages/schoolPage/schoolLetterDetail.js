import React, { useState, useRef, useEffect } from 'react'
import { useParams } from 'react-router-dom'
import axios from 'axios'
import style from './schoolLetterWritePage.module.css'
import { useNavigate } from 'react-router-dom'
import { IoIosArrowBack, IoIosArrowForward } from 'react-icons/io'

const SchoolLetterDetail = () => {
  const { schoolUuid, letterId } = useParams()
  const [nickname, setNickname] = useState('')
  const [content, setContent] = useState('test')
  const [schoolName, setSchoolName] = useState('')

  const navigate = useNavigate()

  useEffect(() => {
    axios
      .get(`https://wishme.co.kr/api/school/letter/one/${letterId}`)
      // .get(`http://localhost:8082/api/school/letter/one/${letterId}`)
      .then((response) => {
        const data = response.data
        // console.log(data)
        setSchoolName(data.schoolName)
        setContent(data.content)
        setSchoolName(data.nickname)
      })
      .catch((error) => {
        // console.error('API 요청 중 오류 발생:', error)
      })
  }, [content])

  const goPre = () => {
    navigate(`/school/${schoolUuid}`)
  }

  return (
    <div className={style.body}>
      <div className={style.schoolName} onClick={() => goPre()}>
        <IoIosArrowBack className={style.icon} />
        이전으로
      </div>
      <div className={style.title}>응원의 말을 확인하세요!</div>
      <div className={style.letterImgBack}>
        <img
          crossOrigin="anonymous"
          src="https://wishme-bichnali.s3.ap-northeast-2.amazonaws.com/letter/clovaLetter.png"
        />
        <div className={style.letterPrefix}>To. {schoolName}</div>
        <textarea
          className={style.contentTextarea}
          value={content}
          onChange={(e) => setContent(e.target.value)}
        />{' '}
        <div className={style.letterPrefix}>From. {nickname}</div>
      </div>
      <div className={style.btn}>
        <div className={style.mySchoolBtn} onClick={() => goPre()}>
          닫기
        </div>
      </div>
    </div>
  )
}

export default SchoolLetterDetail

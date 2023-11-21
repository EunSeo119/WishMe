import React, { useState, useRef, useEffect } from 'react'
import { useParams } from 'react-router-dom'
import axios from 'axios'
import style from './schoolLetterDetail.module.css'
import { useNavigate } from 'react-router-dom'
import { IoIosArrowBack, IoIosArrowForward, IoIosAlert } from 'react-icons/io'
import { PiSirenLight } from 'react-icons/pi'
import LetterReportModal from '../../Modal/letterReportModal'
import tokenHttp from '../../apis/tokenHttp'

const SchoolLetterDetail = () => {
  const { schoolUuid, letterId, page } = useParams()
  const [nickname, setNickname] = useState('')
  const [content, setContent] = useState('')
  const [schoolName, setSchoolName] = useState('')
  const SCHOOL_SERVER = process.env.REACT_APP_SCHOOL_SERVER

  const navigate = useNavigate()

  // 신고하기 모달관련
  const [isModalOpen, setIsModalOpen] = useState(false)
  const openModal = () => {
    setIsModalOpen(true)
  }
  const closeModal = () => {
    setIsModalOpen(false)
  }

  useEffect(() => {
    axios
      .get(`${SCHOOL_SERVER}/api/school/letter/one/${letterId}`)
      .then((response) => {
        const data = response.data
        // console.log(data)
        setSchoolName(data.schoolName)
        setContent(data.content)
        setNickname(data.nickname)
      })
      .catch((error) => {})
  }, [content])

  const goPre = () => {
    navigate(`/school/${schoolUuid}/${page}`)
  }

  return (
    <div className={style.body}>
      <div className={style.schoolName} onClick={() => goPre()}>
        <IoIosArrowBack className={style.icon} />
        이전으로
      </div>
      <div className={style.title}>응원의 편지를 확인하세요!</div>
      <LetterReportModal
        isOpen={isModalOpen}
        onClose={closeModal}
        isSchool={true}
        letterId={letterId}
      />
      <div className={style.letterImgBack}>
        <img
          crossOrigin="anonymous"
          src="https://wishme-bichnali.s3.ap-northeast-2.amazonaws.com/letter/clovaLetter.png"
        />
        {/* 여기가 편지 내용 */}
        <div className={style.letter}>
          <div className={style.to}>
            <text className={style.letterPrefix}>To. {schoolName}</text>
            <PiSirenLight
              className={style.reportLetterIcon}
              onClick={openModal}
            />
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
        <div className={style.mySchoolBtn} onClick={() => goPre()}>
          닫기
        </div>
      </div>
    </div>
  )
}

export default SchoolLetterDetail

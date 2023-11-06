import React, { useState, useRef, useEffect } from 'react'
import { useParams } from 'react-router-dom'
import axios from 'axios'
import style from './replyWritePage.module.css'
import { useNavigate } from 'react-router-dom'
import { IoIosArrowBack, IoIosArrowForward, IoIosAlert } from 'react-icons/io'
import { PiSirenLight } from 'react-icons/pi'
import { BsCheck } from 'react-icons/bs'

import LetterReportModal from '../../Modal/letterReportModal'

const ReplyWritePage = () => {
  const { letterId } = useParams()
  const [content, setContent] = useState('')
  const [letterColor, setLetterColor] = useState('Y')
  const navigate = useNavigate()

  const imageColors = [
    {
      id: 'Y',
      url: 'https://wishme-bichnali.s3.ap-northeast-2.amazonaws.com/letter/yellowButton.png'
    },
    {
      id: 'P',
      url: 'https://wishme-bichnali.s3.ap-northeast-2.amazonaws.com/letter/pinkButton.png'
    },
    {
      id: 'B',
      url: 'https://wishme-bichnali.s3.ap-northeast-2.amazonaws.com/letter/blueButton.png'
    }
  ]

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

  const handleImageClick = (imageId) => {
    // const updatedImages = imageColors.map((image) => {
    //   if (image.id === imageId) {
    //     return { ...image, selected: true }
    //   } else {
    //     return { ...image, selected: false }
    //   }
    // })
    setLetterColor(imageId)
    console.log(letterColor)
  }

  //   useEffect(() => {
  //     axios
  //       .post(`https://wishme.co.kr/api/my/reply/write`, {
  //         myLetterSeq: letterId,
  //         content: content,
  //         color: letterColor
  //       })
  //       .then((response) => {
  //         const data = response.data
  //         console.log(data)
  //         alert('답장 작성 완료')
  //       })
  //       .catch((error) => {
  //         console.error('API 요청 중 오류 발생:', error)
  //       })
  //   }, [content])

  //답장 작성 완료
  const clickWriteLetter = () => {
    if (content) {
      axios
        .post(`https://wishme.co.kr/api/my/reply/write`, {
          myLetterSeq: letterId,
          content: content,
          color: letterColor
        })
        .then((response) => {
          const data = response.data
          console.log(data)
          alert('답장 작성 완료')
          goPre()
        })
        .catch((error) => {
          // console.error('API 요청 중 오류 발생:', error)
        })
    } else {
      alert('내용을 입력해주세요')
    }
  }

  //이미지 변경
  const handleImageColorClick = (color) => {
    setLetterColor(color)
  }

  const goPre = () => {
    navigate(-1)
  }

  return (
    <div className={style.body}>
      <div className={style.schoolName} onClick={() => goPre()}>
        <IoIosArrowBack className={style.icon} />
        이전으로
      </div>
      <div className={style.title}>응원에 답장해 주세요!</div>
      <div className={style.selectColorContainer}>
        {imageColors.map((image) => (
          <div
            key={image.id}
            className={style.imageBox}
            onClick={() => handleImageClick(image.id)}
          >
            <img
              className={style.colorImage}
              src={image.url}
              alt={`Image ${image.id}`}
            />
            {letterColor === image.id && (
              //   <span className={style.checkIcon}>&#10003;</span>
              <BsCheck className={style.checkIcon} />
            )}
          </div>
        ))}
      </div>

      {/*편지지  */}
      <div className={style.letterImgBack}>
        {imageLetter.map((image) => (
          <div
            key={image.id}
            className={style.imageBoxBack}
            onClick={() => handleImageClick(image.id)}
          >
            {letterColor === image.id && (
              <img src={image.url} alt={`Image ${image.id}`} />
            )}
          </div>
        ))}
        <textarea
          className={style.contentTextarea}
          placeholder="편지의 색을 선택하고 답장을 적어주세요."
          value={content}
          onChange={(e) => setContent(e.target.value)}
        />{' '}
      </div>
      {/*버튼  */}
      <div className={style.btn}>
        {/* <div className={style.mySchoolBtn} onClick={() => goPre()}> */}
        <div
          className={`${
            letterColor === 'Y'
              ? style.myBtnYellow
              : letterColor === 'P'
              ? style.myBtnPink
              : style.myBtnBlue
          }`}
          onClick={() => goPre()}
        >
          닫기
        </div>
      </div>
    </div>
  )
}

export default ReplyWritePage

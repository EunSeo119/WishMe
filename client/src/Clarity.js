import React from "react";
import { Helmet } from "react-helmet";

const Clarity = () => {
    return (
        <div>
            <Helmet>
                <script type="text/javascript"  crossorigin="cross-origin">
                    {`
                    (function(c,l,a,r,i,t,y){
                        c[a]=c[a]||function(){(c[a].q=c[a].q||[]).push(arguments)};
                        t=l.createElement(r);t.async=1;t.src="https://www.clarity.ms/tag/"+i;
                        y=l.getElementsByTagName(r)[0];y.parentNode.insertBefore(t,y);
                    })(window, document, "clarity", "script", "jo82kvcijt");
            `}
                </script>
            </Helmet>
            {/* 나머지 컴포넌트 내용 */}
        </div>
    );
};

export default Clarity;

DROP TABLE HR_MEMBER;
CREATE TABLE HR_MEMBER (					
	U_ID  VARCHAR2(20 BYTE) NOT NULL,				
	NAME VARCHAR2(50 CHAR),				
	PASSWD VARCHAR2(50 CHAR),				
	CONSTRAINT PK_MEMBER PRIMARY KEY ( U_ID )				
);					


INSERT INTO hr_member (
    u_id,
    name,
    passwd
) VALUES (
    :u_id,
    :name,
    :passwd
);

commit;

SELECT
    u_id,
    name,
    passwd
FROM
    hr_member
where  u_id = :u_id
;

delete from hr_member;

commit;




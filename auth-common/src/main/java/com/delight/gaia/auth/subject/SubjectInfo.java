package com.delight.gaia.auth.subject;

import com.delight.gaia.base.constant.AccountType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@ToString
public class SubjectInfo {
    protected Long id;
    protected AccountType accountType=AccountType.NORMAL;
}

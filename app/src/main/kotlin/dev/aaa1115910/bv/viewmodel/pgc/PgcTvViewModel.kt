package dev.aaa1115910.bv.viewmodel.pgc

import dev.aaa1115910.biliapi.repositories.PgcRepository
import dev.aaa1115910.biliapi.entity.pgc.PgcType

class PgcTvViewModel(
    override val pgcRepository: PgcRepository
) : PgcViewModel(
    pgcRepository = pgcRepository,
    pgcType = PgcType.Tv
)